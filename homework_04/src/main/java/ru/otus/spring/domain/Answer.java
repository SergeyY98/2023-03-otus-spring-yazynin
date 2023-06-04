package ru.otus.spring.domain;

public class Answer {
  private final String text;

  private final boolean correctness;

  public Answer(String text, boolean correctness) {
    this.text = text;
    this.correctness = correctness;
  }

  public String getText() {
    return text;
  }

  public boolean getCorrectness() {
    return correctness;
  }
}
