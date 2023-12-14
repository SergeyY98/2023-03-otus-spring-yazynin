package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MainMenuService {

  public SendMessage getMainMenuMessage(final long chatId, final String textMessage) {
    final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();

    return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
  }

  private ReplyKeyboardMarkup getMainMenuKeyboard() {

    final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(false);

    List<KeyboardRow> keyboard = new ArrayList<>();

    KeyboardRow row = new KeyboardRow();
    row.add(new KeyboardButton("Мои подписки"));
    keyboard.add(row);
    replyKeyboardMarkup.setKeyboard(keyboard);
    return replyKeyboardMarkup;
  }

  private SendMessage createMessageWithKeyboard(final long chatId,
                                                String textMessage,
                                                final ReplyKeyboardMarkup replyKeyboardMarkup) {
    final SendMessage sendMessage = new SendMessage();
    sendMessage.enableMarkdown(true);
    sendMessage.setChatId(chatId);
    sendMessage.setText(textMessage);
    if (replyKeyboardMarkup != null) {
      sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }
    return sendMessage;
  }
}

