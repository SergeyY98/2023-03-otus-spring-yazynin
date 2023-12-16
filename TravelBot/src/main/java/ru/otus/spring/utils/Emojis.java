package ru.otus.spring.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Emojis {
  ALARM_CLOCK(EmojiParser.parseToUnicode(":alarm_clock:")),
  ARROW_RIGHT(EmojiParser.parseToUnicode(":arrow_right:")),
  PLAIN_DEPARTURE(EmojiParser.parseToUnicode(":airplane_departure:")),
  PLAIN_ARRIVAL(EmojiParser.parseToUnicode(":airplane_arrival:")),
  INFORMATION_SOURCE(EmojiParser.parseToUnicode(":information_source:")),
  SUCCESS_MARK(EmojiParser.parseToUnicode(":white_check_mark:")),
  NOTIFICATION_MARK(EmojiParser.parseToUnicode(":exclamation:")),
  DATE(EmojiParser.parseToUnicode(":date:")),
  MONEY(EmojiParser.parseToUnicode(":moneybag:"));

  private String emojiName;

  @Override
  public String toString() {
    return emojiName;
  }
}

