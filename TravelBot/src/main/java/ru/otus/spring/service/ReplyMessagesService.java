package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.otus.spring.utils.Emojis;

@Service
public class ReplyMessagesService {

  private MessageService messageService;

  @Autowired
  public ReplyMessagesService(MessageService messageService) {
    this.messageService = messageService;
  }

  public SendMessage getReplyMessage(String chatId, String replyMessage) {
    return new SendMessage(chatId, replyMessage);
  }

  public SendMessage getReplyMessage(String chatId, String replyMessage, String... args) {
    return new SendMessage(chatId, messageService.getMessage(replyMessage, args));
  }


  public SendMessage getSuccessReplyMessage(String chatId, String replyMessage) {
    return new SendMessage(chatId, getEmojiReplyText(replyMessage, Emojis.SUCCESS_MARK));
  }

  public SendMessage getWarningReplyMessage(String chatId, String replyMessage) {
    return new SendMessage(chatId, getEmojiReplyText(replyMessage, Emojis.NOTIFICATION_MARK_FAILED));
  }

  public String getReplyText(String replyText) {
    return messageService.getMessage(replyText);
  }

  public String getReplyText(String replyText, String... args) {
    return messageService.getMessage(replyText, args);
  }

  public String getEmojiReplyText(String replyText, Emojis emoji) {
    return messageService.getMessage(replyText, emoji.toString());
  }
}

