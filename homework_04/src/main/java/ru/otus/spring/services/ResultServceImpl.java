package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.LimitScoreProvider;
import ru.otus.spring.domain.Result;

@Service
public class ResultServceImpl implements ResultService {

  private final IOService ioService;

  private final MessageService messageService;

  private final LimitScoreProvider limitScoreProvider;

  @Autowired
  public ResultServceImpl(IOService ioService, MessageService messageService, LimitScoreProvider limitScoreProvider) {
    this.limitScoreProvider = limitScoreProvider;
    this.ioService = ioService;
    this.messageService = messageService;
  }

  @Override
  public void print(Result result) {
    ioService.outputString(messageService.getMessage("resultMessageMsg",
            result.getStudent().getName(), result.getStudent().getSurname(),
                Integer.toString(result.getScore()), Integer.toString(limitScoreProvider.getLimitScore())));
  }
}
