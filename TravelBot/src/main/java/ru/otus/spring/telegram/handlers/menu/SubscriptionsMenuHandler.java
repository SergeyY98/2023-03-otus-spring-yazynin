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
  private final ReplyMessagesService messagesService;

  public SubscriptionsMenuHandler(FlightSubscriptionService subscribeService,
                                  UserDataCache userDataCache,
                                  ReplyMessagesService messagesService,
                                  @Lazy TravelBot travelBot) {
    this.subscribeService = subscribeService;
    this.messagesService = messagesService;
    this.userDataCache = userDataCache;
    this.travelBot = travelBot;
  }

  @Override
  public SendMessage handle(Message message) {
    List<FlightSubscription> usersSubscriptions = subscribeService.getUsersSubscriptions(message.getChatId());

    if (usersSubscriptions.isEmpty()) {
      userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);
      return messagesService.getReplyMessage(message.getChatId().toString(), "У вас нет активных подписок.");
    }

    for (FlightSubscription subscription : usersSubscriptions) {

      String subscriptionInfo = messagesService.getReplyText("subscription.flightTicketsPriceChanges",
          Emojis.NOTIFICATION_BELL.toString(), subscription.getSegments().get(0).getDepartureCityName(),
          subscription.getSegments().get(0).getArrivalCityName(), subscription.getSegments().get(0).getDepartureTime(),
          subscription.getSegments().get(0).getArrivalTime()) + messagesService.getReplyText("subscription.lastTicketPrices");

      //Посылаем кнопку "Отписаться" с ID подписки
      String unsubscribeData = String.format("%s|%s", CallbackQueryType.UNSUBSCRIBE, subscription.getToken());
      travelBot.sendInlineKeyBoardMessage(message.getChatId(), subscriptionInfo, "Отписаться", unsubscribeData);
    }

    userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);

    return messagesService.getSuccessReplyMessage(message.getChatId().toString(), "reply.subscriptions.listLoaded");
  }

  @Override
  public BotState getHandlerName() {
    return BotState.SHOW_SUBSCRIPTIONS_MENU;
  }


}