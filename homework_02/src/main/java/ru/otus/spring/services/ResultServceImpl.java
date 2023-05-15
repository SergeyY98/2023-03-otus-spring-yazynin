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
  public void print(int score) {
    var result = new Result(score, passedScore);
    ioService.outputString(String.format("Answered: %d Needed: %d Result: %s", score, passedScore, result.getStatus()));
  }
}
