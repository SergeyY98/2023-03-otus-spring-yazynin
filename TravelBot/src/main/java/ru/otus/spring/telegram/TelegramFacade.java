package ru.otus.spring.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.otus.spring.cache.UserDataCache;
import ru.otus.spring.telegram.handlers.callbackquery.CallbackQueryFacade;

@Service
@Slf4j
public class TelegramFacade {
  private UserDataCache userDataCache;
  private BotStateContext botStateContext;
  private CallbackQueryFacade callbackQueryFacade;

  @Autowired
  public TelegramFacade(UserDataCache userDataCache, BotStateContext botStateContext,
                        CallbackQueryFacade callbackQueryFacade) {
    this.userDataCache = userDataCache;
    this.botStateContext = botStateContext;
    this.callbackQueryFacade = callbackQueryFacade;
  }

  public SendMessage handleUpdate(Update update) {
    SendMessage replyMessage = null;

    if (update.hasCallbackQuery()) {
      log.info("New callbackQuery from User: {} with data: {}", update.getCallbackQuery().getFrom().getUserName(),
          update.getCallbackQuery().getData());
      return callbackQueryFacade.processCallbackQuery(update.getCallbackQuery());
    }


    Message message = update.getMessage();
    if (message != null && message.hasText()) {
      replyMessage = handleInputMessage(message);
    }

    return replyMessage;
  }

  private SendMessage handleInputMessage(Message message) {
    String inputMsg = message.getText();
    long userId = message.getFrom().getId();
    BotState botState;
    SendMessage replyMessage;

    botState = switch (inputMsg) {
      case "Найти рейсы" -> BotState.FLIGHTS_SEARCH;
      case "Мои подписки" -> BotState.SHOW_SUBSCRIPTIONS_MENU;
      default -> userDataCache.getUsersCurrentBotState(userId);
    };

    userDataCache.setUsersCurrentBotState(userId, botState);

    replyMessage = botStateContext.processInputMessage(botState, message);

    return replyMessage;
  }


}
