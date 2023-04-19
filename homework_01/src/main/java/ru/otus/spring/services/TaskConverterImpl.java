package ru.otus.spring.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Task;

public class TaskConverterImpl implements TaskConverter {

  @Override
  public String convertTaskToString(int taskNumber, Task task) {
    List<Answer> answers = task.getAnswers();
    String taskString = taskNumber + ". " + task.getQuestion() + "\n";
    for (var answerNumber = 0; answerNumber < answers.size(); answerNumber++) {
      taskString = taskString.concat((answerNumber + 1) + ") " + answers.get(answerNumber).getText() + " ");
    }
    return taskString;
  }

  @Override
  public Task convertStringToTask(final String from) {
    List<String> data = Arrays.asList(from.split(";"));
    List<String> answerTexts = data.subList(1, data.size());
    List<Answer> answers = new ArrayList<>();
    String question = data.get(0);
    for (var answerNumber = 0; answerNumber < answerTexts.size(); answerNumber++) {
      answers.add(new Answer(answerTexts.get(answerNumber), answerNumber == 0));
    }
    return new Task(question, answers);
  }
}
