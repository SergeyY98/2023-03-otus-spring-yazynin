package ru.otus.spring.telegram.handlers.callbackquery;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserChatButtonStatus {
  SUBSCRIBED("Отписаться"), UNSUBSCRIBED("Подписаться");

  private String buttonStatus;

  @Override
  public String toString() {
    return buttonStatus;
  }
}
