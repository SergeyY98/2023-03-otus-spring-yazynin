package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.domain.Result;

@Service
public class ResultServceImpl implements ResultService {

  private final IOService ioService;

  private final MessageService messageService;

  private final AppProps appProps;

  @Autowired
  public ResultServceImpl(IOService ioService, MessageService messageService, AppProps appProps) {
    this.appProps = appProps;
    this.ioService = ioService;
    this.messageService = messageService;
  }

  @Override
  public void print(Result result) {
    ioService.outputString(messageService.getMessage("resultMessageMsg",
            result.getStudent().getName(), result.getStudent().getSurname(),
                Integer.toString(result.getScore()), Integer.toString(appProps.getLimitScore())));
  }
}
