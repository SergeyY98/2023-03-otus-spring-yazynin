package ru.otus.spring.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.otus.spring.telegram.handlers.InputMessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BotStateContext {
  private final Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();

  @Autowired
  public BotStateContext(List<InputMessageHandler> messageHandlers) {
    messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
  }

  public SendMessage processInputMessage(BotState currentState, Message message) {
    log.info("New message from User:{}, chatId: {}, with text: {} with state {}",
        message.getFrom().getUserName(), message.getChatId(), message.getText(), currentState);
    log.info("Avaliable message handlers:{}", messageHandlers);
    InputMessageHandler currentMessageHandler = messageHandlers.get(currentState);
    log.info("For user:{} picked InputHandler {}",
        message.getFrom().getUserName(), currentState);
    return currentMessageHandler.handle(message);
  }


  private InputMessageHandler findMessageHandler(BotState currentState) {
    switch (currentState) {
      case FLIGHTS_SEARCH:
      case ASK_SEARCH_PARAMS:
        return messageHandlers.get(BotState.FLIGHTS_SEARCH);
      default:
        return messageHandlers.get(currentState);
    }
  }

}
