package ru.otus.spring.services;

import java.util.Arrays;
import ru.otus.spring.domain.Task;

public class TaskConverterImpl implements TaskConverter {

  @Override
  public String convertTaskToString(int taskNumber, Task task) {
    StringBuilder builder = new StringBuilder();
    String[] answers = task.getAnswers();
    builder.append(taskNumber + ". " + task.getQuestion() + "\n");
    for (var answerNumber = 0; answerNumber < answers.length; answerNumber++) {
      builder.append((answerNumber + 1) + ") " + answers[answerNumber] + " ");
    }
    String s = builder.toString();
    return s;
  }

  @Override
  public Task convertStringToTask(final String from) {
    String[] data = from.split(";");
    String[] answers = Arrays.copyOfRange(data, 1, data.length);
    String question = data[0];
    return new Task(question, answers);
  }
}
