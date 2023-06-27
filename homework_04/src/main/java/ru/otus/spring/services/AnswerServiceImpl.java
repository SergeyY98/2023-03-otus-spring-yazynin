package ru.otus.spring.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.exceptions.AnswerIndexOutOfBoundsException;

@Service
public class AnswerServiceImpl implements AnswerService {

  @Override
  public void checkAnswerNumber(int answerNumber, int answersCount) {
    if (answerNumber <= 0 || answerNumber > answersCount) {
      throw new AnswerIndexOutOfBoundsException("Given number of answer is out of range");
    }
  }
}
