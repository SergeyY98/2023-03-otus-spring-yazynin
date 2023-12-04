package ru.otus.spring.telegram.handlers.callbackquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.service.FlightSubscriptionService;
import ru.otus.spring.service.ReplyMessagesService;
import ru.otus.spring.telegram.TravelBot;
import ru.otus.spring.utils.Emojis;

import java.util.Optional;

@Component
public class UnsubscribeFlightQueryHandler implements CallbackQueryHandler {
  private static final CallbackQueryType HANDLER_QUERY_TYPE = CallbackQueryType.UNSUBSCRIBE;
  private final FlightSubscriptionService subscriptionService;
  private final ReplyMessagesService messagesService;
  private final TravelBot travelBot;

  @Autowired
  public UnsubscribeFlightQueryHandler(FlightSubscriptionService subscriptionService,
                                            ReplyMessagesService messagesService,
                                            @Lazy TravelBot travelBot) {
    this.subscriptionService = subscriptionService;
    this.messagesService = messagesService;
    this.travelBot = travelBot;
  }

  @Override
  public CallbackQueryType getHandlerQueryType() {
    return HANDLER_QUERY_TYPE;
  }

  @Override
  public SendMessage handleCallbackQuery(CallbackQuery callbackQuery) {
    final long chatId = callbackQuery.getMessage().getChatId();

    final String token = callbackQuery.getData();
    Optional<FlightSubscription> optionalUserSubscription = subscriptionService.getUsersSubscriptionByToken(token);
    if (optionalUserSubscription.isEmpty()) {
      return messagesService.getReplyMessage(String.valueOf(chatId), "reply.query.flight.userHasNoSubscription");
    }

    FlightSubscription userSubscription = optionalUserSubscription.get();
    subscriptionService.deleteUserSubscription(token);

    travelBot.sendChangedInlineButtonText(callbackQuery,
        String.format("%s %s", Emojis.SUCCESS_UNSUBSCRIBED, UserChatButtonStatus.UNSUBSCRIBED),
        CallbackQueryType.QUERY_PROCESSED.name());

    return messagesService.getReplyMessage(String.valueOf(chatId), "Удалена подписка на рейс.");
  }


}