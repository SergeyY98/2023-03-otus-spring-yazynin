package ru.otus.spring.telegram.handlers.callbackquery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.otus.spring.cache.UserDataCache;
import ru.otus.spring.dto.Flight;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.dto.SegmentDto;
import ru.otus.spring.service.FlightSubscriptionService;
import ru.otus.spring.service.ParseQueryDataService;
import ru.otus.spring.service.ReplyMessagesService;
import ru.otus.spring.telegram.TravelBot;
import ru.otus.spring.utils.Emojis;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class SubscribeTicketsInfoQueryHandler implements CallbackQueryHandler {
  private static final CallbackQueryType HANDLER_QUERY_TYPE = CallbackQueryType.SUBSCRIBE;

  private final FlightSubscriptionService subscriptionService;

  private final ParseQueryDataService parseService;

  private final ReplyMessagesService messagesService;

  private final UserDataCache userDataCache;

  private final TravelBot travelBot;

  @Autowired
  public SubscribeTicketsInfoQueryHandler(FlightSubscriptionService subscribeService, ParseQueryDataService parseService,
                                   ReplyMessagesService messagesService, UserDataCache userDataCache,
                                          @Lazy TravelBot travelBot) {
    this.subscriptionService = subscribeService;
    this.parseService = parseService;
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

    final String offerKeyToHighlight = parseService.parseOfferKeyToHighlightSubscribeQuery(callbackQuery);

    Optional<FlightSubscription> userSubscriptionOptional = parseQueryData(callbackQuery);
    if (userSubscriptionOptional.isEmpty()) {
      return messagesService.getReplyMessage(chatId, "reply.query.searchAgain");
    }

    FlightSubscription userSubscription = userSubscriptionOptional.get();

    log.info("Подписка на рейс {}", userSubscription.getOfferKeyToHighlight());
    subscriptionService.saveUserSubscription(userSubscription);

    String subscriptionButtonText = String.format("%s %s", Emojis.ALARM_CLOCK, UserChatButtonStatus.SUBSCRIBED);
    String subscriptionInfoData = String.format("%s|%s", CallbackQueryType.UNSUBSCRIBE.name(), offerKeyToHighlight);
    travelBot.sendChangedInlineButtonText(callbackQuery, subscriptionButtonText, subscriptionInfoData);

    return messagesService.getReplyMessage(chatId, "reply.query.flight.subscribed",
        offerKeyToHighlight.split("_")[1]);

  }

  private Optional<FlightSubscription> parseQueryData(CallbackQuery callbackQuery) {
    final String offerKeyToHighlight = parseService.parseOfferKeyToHighlightSubscribeQuery(callbackQuery);
    List<Flight> foundedFlights = userDataCache.getSearchFoundedFlights(callbackQuery.getMessage().getChatId());
    final long chatId = callbackQuery.getMessage().getChatId();

    Optional<Flight> queriedFlightOptional = foundedFlights.stream().
        filter(flight -> flight.getOfferKeyToHighlight().equals(offerKeyToHighlight)).
        findFirst();

    if (queriedFlightOptional.isEmpty()) {
      return Optional.empty();
    }

    Flight flight = queriedFlightOptional.get();

    return Optional.of(new FlightSubscription(flight.getToken(), offerKeyToHighlight,
        flight.getSegments().stream().map(SegmentDto::toDomainObject).toList(), flight.getPriceBreakdown(), chatId));
  }
}
