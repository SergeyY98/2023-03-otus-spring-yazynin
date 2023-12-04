package ru.otus.spring.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
  PLAIN_DEPARTURE(EmojiParser.parseToUnicode(":airplane_departure:")),
  PLAIN_ARRIVAL(EmojiParser.parseToUnicode(":airplane_arrival:")),
  TIME_DEPART(EmojiParser.parseToUnicode(":clock8:")),
  TIME_ARRIVAL(EmojiParser.parseToUnicode(":clock3:")),
  SUCCESS_MARK(EmojiParser.parseToUnicode(":white_check_mark:")),
  NOTIFICATION_MARK_FAILED(EmojiParser.parseToUnicode(":exclamation:")),
  SUCCESS_UNSUBSCRIBED(EmojiParser.parseToUnicode(":negative_squared_cross_mark:")),
  SUCCESS_SUBSCRIBED(EmojiParser.parseToUnicode(":mailbox:")),
  NOTIFICATION_BELL(EmojiParser.parseToUnicode(":bell:")),
  NOTIFICATION_PRICE_DOWN(EmojiParser.parseToUnicode(":chart_with_downwards_trend:"));

  private String emojiName;

  @Override
  public String toString() {
    return emojiName;
  }
}

