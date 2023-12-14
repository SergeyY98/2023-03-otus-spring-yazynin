package ru.otus.spring.telegram.handlers.callbackquery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.otus.spring.service.ParseQueryDataService;
import ru.otus.spring.service.ReplyMessagesService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CallbackQueryFacade {
  private final ReplyMessagesService messagesService;

  private final ParseQueryDataService parseService;

  private final List<CallbackQueryHandler> callbackQueryHandlers;

  @Autowired
  public CallbackQueryFacade(ReplyMessagesService messagesService, ParseQueryDataService parseService,
                             List<CallbackQueryHandler> callbackQueryHandlers) {
    this.messagesService = messagesService;
    this.parseService = parseService;
    this.callbackQueryHandlers = callbackQueryHandlers;
  }

  public SendMessage processCallbackQuery(CallbackQuery usersQuery) {
    CallbackQueryType usersQueryType = CallbackQueryType
        .valueOf(parseService.parseCallbackQueryType(usersQuery));
    log.info("CallbackQueryType: {}", usersQueryType);
    Optional<CallbackQueryHandler> queryHandler = callbackQueryHandlers.stream().
        filter(callbackQuery -> callbackQuery.getHandlerQueryType().equals(usersQueryType)).findFirst();

    return queryHandler.map(handler -> handler.handleCallbackQuery(usersQuery)).
        orElse(messagesService.getReplyMessage(usersQuery.getMessage().getChatId().toString(), "reply.query.failed"));
  }
}

