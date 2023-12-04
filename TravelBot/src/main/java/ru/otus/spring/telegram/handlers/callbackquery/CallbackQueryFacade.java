package ru.otus.spring.telegram.handlers.callbackquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.otus.spring.service.ReplyMessagesService;

import java.util.List;
import java.util.Optional;

@Component
public class CallbackQueryFacade {
  private final ReplyMessagesService messagesService;
  private final List<CallbackQueryHandler> callbackQueryHandlers;

  @Autowired
  public CallbackQueryFacade(ReplyMessagesService messagesService,
                             List<CallbackQueryHandler> callbackQueryHandlers) {
    this.messagesService = messagesService;
    this.callbackQueryHandlers = callbackQueryHandlers;
  }

  public SendMessage processCallbackQuery(CallbackQuery usersQuery) {
    CallbackQueryType usersQueryType = CallbackQueryType.valueOf(usersQuery.getData());

    Optional<CallbackQueryHandler> queryHandler = callbackQueryHandlers.stream().
        filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType)).findFirst();

    return queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery)).
        orElse(messagesService.getWarningReplyMessage(usersQuery.getMessage().getChatId().toString(), "reply.query.failed"));
  }
}

