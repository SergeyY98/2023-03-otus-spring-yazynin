package ru.otus.spring.telegram.handlers.callbackquery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.service.FlightSubscriptionService;
import ru.otus.spring.service.ParseQueryDataService;
import ru.otus.spring.service.ReplyMessagesService;
import ru.otus.spring.telegram.TravelBot;
import ru.otus.spring.utils.Emojis;

import java.util.List;

@Slf4j
@Component
public class UnsubscribeFlightQueryHandler implements CallbackQueryHandler {
  private final FlightSubscriptionService subscriptionService;

  private final ParseQueryDataService parseService;

  private final ReplyMessagesService messagesService;

  private final TravelBot travelBot;

  @Autowired
  public UnsubscribeFlightQueryHandler(FlightSubscriptionService subscriptionService,
                                       ParseQueryDataService parseService,
                                       ReplyMessagesService messagesService,
                                       @Lazy TravelBot travelBot) {
    this.subscriptionService = subscriptionService;
    this.parseService = parseService;
    this.messagesService = messagesService;
    this.travelBot = travelBot;
  }

  @Override
  public CallbackQueryType getHandlerQueryType() {
    return CallbackQueryType.UNSUBSCRIBE;
  }

  @Override
  public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
    final long chatId = callbackQuery.getMessage().getChatId();

    final String offerKeyToHighlight = parseService.parseOfferKeyToHighlightSubscribeQuery(callbackQuery);
    List<FlightSubscription> flightSubscriptions = subscriptionService
        .getUsersSubscriptionByOfferKeyToHighlight(offerKeyToHighlight);
    log.info("Отписка от рейса {}", offerKeyToHighlight);
    if (flightSubscriptions.isEmpty()) {
      return messagesService.getReplyMessage(String.valueOf(chatId), "reply.query.flight.userHasNoSubscription");
    }

    FlightSubscription flightSubscription = flightSubscriptions.get(0);
    subscriptionService.deleteUserSubscription(flightSubscription);

    String subscriptionButtonText = String.format("%s %s", Emojis.ALARM_CLOCK, UserChatButtonStatus.UNSUBSCRIBED);
    String subscriptionInfoData = String.format("%s|%s", CallbackQueryType.SUBSCRIBE.name(), offerKeyToHighlight);
    travelBot.sendChangedInlineButtonText(callbackQuery, subscriptionButtonText, subscriptionInfoData);

    return messagesService.getReplyMessage(String.valueOf(chatId), "reply.query.flight.unsubscribed",
        offerKeyToHighlight.split("_")[1]);
  }


}