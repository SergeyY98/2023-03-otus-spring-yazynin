package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Result;

@Service
public class ResultServceImpl implements ResultService {

  private final int passedScore;

  @Autowired
  public ResultServceImpl(@Value("${passed.score}") int passedScore) {
    this.passedScore = passedScore;
  }

  @Override
  public String returnResult(int score) {
    var result = new Result(score, passedScore);
    return String.format("%d/%d %s", score, passedScore, result.getStatus());
  }
}
