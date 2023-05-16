package ru.otus.spring.domain;

public class Result {
  private final Student student;

  private final int score;

  public Result(Student student, int score) {
    this.student = student;
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public Student getStudent() {
    return student;
  }
}
