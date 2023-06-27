package ru.otus.spring.configs;

public interface LimitScoreProvider {

  int getLimitScore();

  void setLimitScore(int limitScore);
}
