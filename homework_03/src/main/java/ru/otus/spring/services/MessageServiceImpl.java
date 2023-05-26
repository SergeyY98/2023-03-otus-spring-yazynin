package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageServiceImpl implements MessageService {

  private final MessageSource messageSource;

  private final Locale locale;

  @Autowired
  public MessageServiceImpl(MessageSource messageSource, Locale locale) {
    this.messageSource = messageSource;
    this.locale = locale;
  }

  @Override
  public String getMessage(String msgCode) {
    return messageSource.getMessage(msgCode, null, locale);
  }

  @Override
  public String getMessage(String msgCode, String... strings) {
    return messageSource.getMessage(msgCode, strings, locale);
  }
}
