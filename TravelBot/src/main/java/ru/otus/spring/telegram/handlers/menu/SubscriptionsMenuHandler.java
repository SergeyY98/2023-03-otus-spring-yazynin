package ru.otus.spring.telegram.handlers.menu;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.otus.spring.cache.UserDataCache;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.service.FlightSubscriptionService;
import ru.otus.spring.service.ReplyMessagesService;
import ru.otus.spring.telegram.BotState;
import ru.otus.spring.telegram.TravelBot;
import ru.otus.spring.telegram.handlers.InputMessageHandler;
import ru.otus.spring.telegram.handlers.callbackquery.CallbackQueryType;
import ru.otus.spring.utils.Emojis;

import java.util.List;

@Component
public class SubscriptionsMenuHandler implements InputMessageHandler {
  private final FlightSubscriptionService subscribeService;

  private final TravelBot travelBot;

  private final UserDataCache userDataCache;

  private final ReplyMessagesService messageService;

  public SubscriptionsMenuHandler(FlightSubscriptionService subscribeService,
                                  UserDataCache userDataCache,
                                  ReplyMessagesService messageService,
                                  @Lazy TravelBot travelBot) {
    this.subscribeService = subscribeService;
    this.messageService = messageService;
    this.userDataCache = userDataCache;
    this.travelBot = travelBot;
  }

  @Override
  public SendMessage handle(Message message) {
    List<FlightSubscription> usersSubscriptions = subscribeService.getUsersSubscriptions(message.getChatId());
    if (usersSubscriptions.isEmpty()) {
      userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);
      return messageService.getReplyMessage(message.getChatId().toString(),
          "reply.subscriptions.userHasNoSubscriptions");
    }
    for (FlightSubscription subscription : usersSubscriptions) {
      String subscriptionInfo = messageService.getReplyText("reply.flight.infoMessage",
          Emojis.INFORMATION_SOURCE.toString(), subscription.getOfferKeyToHighlight().split("_")[1],
          Emojis.PLAIN_DEPARTURE.toString(), subscription.getSegments().get(0).getDepartureCityName(),
          Emojis.DATE.toString(), subscription.getSegments().get(0).getDepartureTime(),
          Emojis.PLAIN_ARRIVAL.toString(), subscription.getSegments().get(0).getArrivalCityName(),
          Emojis.DATE.toString(), subscription.getSegments().get(0).getArrivalTime(),
          Emojis.MONEY.toString(), subscription.getPriceBreakdown().getTotal().getUnits().toString(),
          subscription.getPriceBreakdown().getTotal().getCurrencyCode().toString());
      String unsubscribeData = String.format("%s|%s", CallbackQueryType.UNSUBSCRIBE,
          subscription.getOfferKeyToHighlight());
      travelBot.sendInlineKeyBoardMessage(message.getChatId(), subscriptionInfo,
          Emojis.ALARM_CLOCK + " Отписаться", unsubscribeData);
    }
    userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);

    return messageService.getSuccessReplyMessage(message.getChatId().toString(), "reply.subscriptions.listLoaded");
  }

  @Override
  public BotState getHandlerName() {
    return BotState.SHOW_SUBSCRIPTIONS_MENU;
  }


}
