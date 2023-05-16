package ru.otus.spring.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Task;

@Service
public class TaskConverterImpl implements TaskConverter {

  @Override
  public String convertTaskToString(int taskNumber, Task task) {
    List<Answer> answers = task.getAnswers();
    String taskString = taskNumber + ". " + task.getQuestion() + System.lineSeparator();
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
    Collections.shuffle(answers);
    return new Task(question, answers);
  }
}
