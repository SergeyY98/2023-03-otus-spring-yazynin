package ru.otus.spring.telegram.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.otus.spring.cache.UserDataCache;
import ru.otus.spring.domain.Segment;
import ru.otus.spring.dto.Flight;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.dto.PriceBreakdown;
import ru.otus.spring.dto.SegmentDto;
import ru.otus.spring.service.FlightSubscriptionService;
import ru.otus.spring.service.ReplyMessagesService;
import ru.otus.spring.telegram.TravelBot;
import ru.otus.spring.telegram.handlers.callbackquery.CallbackQueryHandler;
import ru.otus.spring.telegram.handlers.callbackquery.CallbackQueryType;
import ru.otus.spring.telegram.handlers.callbackquery.UserChatButtonStatus;
import ru.otus.spring.utils.Emojis;

import java.util.List;
import java.util.Optional;

@Component
public class FlightSubscriptionHandler implements CallbackQueryHandler {
  private static final CallbackQueryType HANDLER_QUERY_TYPE = CallbackQueryType.SUBSCRIBE;
  private final FlightSubscriptionService subscriptionService;
  private final ReplyMessagesService messagesService;
  private final UserDataCache userDataCache;
  private final TravelBot travelBot;

  @Autowired
  public FlightSubscriptionHandler(FlightSubscriptionService subscribeService,
                                          ReplyMessagesService messagesService,
                                          UserDataCache userDataCache,
                                          @Lazy TravelBot travelBot) {
    this.subscriptionService = subscribeService;
    this.messagesService = messagesService;
    this.userDataCache = userDataCache;
    this.travelBot = travelBot;
  }
  @Override
  public CallbackQueryType getHandlerQueryType() {
    return HANDLER_QUERY_TYPE;
  }

  @Override
  public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
    final String chatId = callbackQuery.getMessage().getChatId().toString();
    final String token = callbackQuery.getData();

    Optional<FlightSubscription> userSubscriptionOptional = parseQueryData(callbackQuery);
    if (userSubscriptionOptional.isEmpty()) {
      return messagesService.getReplyMessage(chatId, "Запрос устарел, чтобы подписаться, повторите поиск.");
    }

    FlightSubscription userSubscription = userSubscriptionOptional.get();
    if (subscriptionService.hasTicketsSubscription(userSubscription)) {
      return messagesService.getReplyMessage(chatId, "Вы уже подписаны на этот рейс.");
    }

    subscriptionService.saveUserSubscription(userSubscription);

    travelBot.sendChangedInlineButtonText(callbackQuery,
        String.format("%s %s", Emojis.SUCCESS_SUBSCRIBED, UserChatButtonStatus.SUBSCRIBED), CallbackQueryType.QUERY_PROCESSED.name());

    return messagesService.getReplyMessage(chatId, "Оформлена подписка на рейс.");

  }


  private Optional<FlightSubscription> parseQueryData(CallbackQuery usersQuery) {
    List<Flight> foundedFlights = userDataCache.getSearchFoundedFlights(usersQuery.getMessage().getChatId());
    final long chatId = usersQuery.getMessage().getChatId();

    final String token = usersQuery.getData();

    Optional<Flight> queriedFlightOptional = foundedFlights.stream().
        filter(flight -> flight.getToken().equals(token)).
        findFirst();

    if (queriedFlightOptional.isEmpty()) {
      return Optional.empty();
    }

    Flight queriedFlight = queriedFlightOptional.get();
    final List<Segment> segments = queriedFlight.getSegments().stream().map(SegmentDto::toDomainObject).toList();
    final PriceBreakdown priceBreakdown = queriedFlight.getPriceBreakdown();

    return Optional.of(new FlightSubscription(0, token, segments, priceBreakdown, chatId));
  }
}
