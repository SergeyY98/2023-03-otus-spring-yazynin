package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.AppProps;

@Service
public class MessageServiceImpl implements MessageService {

  private final MessageSource messageSource;

  private final AppProps props;

  @Autowired
  public MessageServiceImpl(MessageSource messageSource, AppProps props) {
    this.messageSource = messageSource;
    this.props = props;
  }

  @Override
  public String getMessage(String msgCode) {
    return messageSource.getMessage(msgCode, null, props.getLocale());
  }

  @Override
  public String getMessage(String msgCode, String... strings) {
    return messageSource.getMessage(msgCode, strings, props.getLocale());
  }
}
