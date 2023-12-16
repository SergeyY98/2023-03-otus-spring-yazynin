package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.LocaleProvider;

@Service
public class MessageServiceImpl implements MessageService {

  private final MessageSource messageSource;

  private final LocaleProvider localeProvider;

  @Autowired
  public MessageServiceImpl(MessageSource messageSource, LocaleProvider localeProvider) {
    this.messageSource = messageSource;
    this.localeProvider = localeProvider;
  }

  @Override
  public String getMessage(String msgCode) {
    return messageSource.getMessage(msgCode, null, localeProvider.getLocale());
  }

  @Override
  public String getMessage(String msgCode, String... strings) {
    return messageSource.getMessage(msgCode, strings, localeProvider.getLocale());
  }
}
