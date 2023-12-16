package ru.otus.spring.telegram.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.otus.spring.telegram.BotState;

public interface InputMessageHandler {
  SendMessage handle(Message message);

  BotState getHandlerName();
}