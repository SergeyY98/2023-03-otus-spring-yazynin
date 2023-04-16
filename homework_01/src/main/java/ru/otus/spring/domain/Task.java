package ru.otus.spring.domain;

import java.util.List;
import java.util.UUID;

public class Task {
  private final String id;

  private final String question;

  private final List<Answer> answers;

  public Task(String question, List<Answer> answers) {
    this.id = UUID.randomUUID().toString();
    this.question = question;
    this.answers = answers;
  }

  public Task(String id, String question, List<Answer> answers) {
    this.id = id;
    this.question = question;
    this.answers = answers;
  }

  public String getId() {
    return id;
  }

  public String getQuestion() {
    return question;
  }

  public List<Answer> getAnswers() {
    return answers;
  }

  public Task copy() {
    return new Task(id, question, answers);
  }
}
