package ru.otus.spring.services.processors.utils;

import ru.otus.spring.exceptions.AnswerIndexOutOfBoundsException;

public class AnswerListUtil {
  public static void checkAnswerNumber(int answerNumber, int answersCount) {
    if (answerNumber <= 0 || answerNumber > answersCount) {
      throw new AnswerIndexOutOfBoundsException("Given number of answer is out of range");
    }
  }
}
