package ru.otus.spring.domain;

public class Result {
  private final int score;

  private final int passedScore;

  private final String status;

  public Result(int score, int passedScore) {
    this.score = score;
    this.passedScore = passedScore;
    this.status = passedScore <= score ? "passed" : "failed";
  }

  public int getPassedScore() {
    return passedScore;
  }

  public int getScore() {
    return score;
  }

  public String getStatus() {
    return status;
  }
}
