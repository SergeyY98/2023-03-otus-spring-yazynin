package ru.otus.spring.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.otus.spring.telegram.handlers.FlightSearchHandler;
import ru.otus.spring.telegram.handlers.callbackquery.CallbackQueryHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TravelBot extends TelegramWebhookBot {

  String botPath;

  String botUsername;

  String botToken;

  private TelegramFacade telegramFacade;

  @Autowired
  public TravelBot(TelegramFacade telegramFacade) {
    this.telegramFacade = telegramFacade;
  }

  @Override
  public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
    return telegramFacade.handleUpdate(update);
  }

  public void sendMessage(long chatId, String textMessage) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(textMessage);

    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  public void sendMessage(SendMessage sendMessage) {
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  public void sendInlineKeyBoardMessage(long chatId, String messageText, String buttonText, String callbackData) {
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
    keyboardButton.setText(buttonText);

    if (callbackData != null) {
      keyboardButton.setCallbackData(callbackData);
    }

    List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
    keyboardButtonsRow1.add(keyboardButton);

    List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
    rowList.add(keyboardButtonsRow1);

    inlineKeyboardMarkup.setKeyboard(rowList);

    SendMessage outMessage = new SendMessage();
    outMessage.setChatId(chatId);
    outMessage.setText(messageText);
    outMessage.setReplyMarkup(inlineKeyboardMarkup);
    try {
      execute(outMessage);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }


  public void sendAnswerCallbackQuery(String callbackId, String message) {
    AnswerCallbackQuery answer = new AnswerCallbackQuery();
    answer.setCallbackQueryId(callbackId);
    answer.setText(message);
    answer.setShowAlert(true);

    try {
      execute(answer);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
  public void sendChangedInlineButtonText(CallbackQuery callbackQuery, String buttonText, String callbackData) {
    final InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    final List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
    final long message_id = callbackQuery.getMessage().getMessageId();
    final long chat_id = callbackQuery.getMessage().getChatId();
    final List<List<InlineKeyboardButton>> rowList = new ArrayList<>();


    InlineKeyboardButton subscribeButton = new InlineKeyboardButton();
    subscribeButton.setText(buttonText);
    subscribeButton.setCallbackData(callbackData);
    keyboardButtonsRow1.add(subscribeButton);
    rowList.add(keyboardButtonsRow1);
    inlineKeyboardMarkup.setKeyboard(rowList);

    EditMessageText editMessageText = new EditMessageText();
    editMessageText.setChatId(chat_id);
    editMessageText.setMessageId((int) (message_id));
    editMessageText.setText(callbackQuery.getMessage().getText());

    editMessageText.setReplyMarkup(inlineKeyboardMarkup);
    try {
      execute(editMessageText);
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
