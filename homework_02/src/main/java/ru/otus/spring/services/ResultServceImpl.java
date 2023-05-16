package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Result;

@Service
public class ResultServceImpl implements ResultService {

  private final IOService ioService;

  private final int passedScore;

  @Autowired
  public ResultServceImpl(IOService ioService, @Value("${passed.score}") int passedScore) {
    this.ioService = ioService;
    this.passedScore = passedScore;
  }

  @Override
  public void print(Result result) {
    ioService.outputString(String.format("%s %s answered: %d needed: %d result: %s", result.getStudent().getName(),
        result.getStudent().getSurname(), result.getScore(),
        passedScore, passedScore <= result.getScore() ? "passed" : "failed"));
  }
}
