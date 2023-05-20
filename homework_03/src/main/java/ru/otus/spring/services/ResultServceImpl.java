package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.domain.Result;

@Service
public class ResultServceImpl implements ResultService {

  private final IOService ioService;

  private final MessageSource messageSource;

  private final AppProps props;

  @Autowired
  public ResultServceImpl(IOService ioService, MessageSource messageSource, AppProps props) {
    this.ioService = ioService;
    this.messageSource = messageSource;
    this.props = props;
  }

  @Override
  public void print(Result result) {
    ioService.outputString(messageSource.getMessage("resultMessage",
            new String[]{result.getStudent().getName(), result.getStudent().getSurname(),
                Integer.toString(result.getScore()), props.getMessage()}, props.getLocale()));
  }
}
