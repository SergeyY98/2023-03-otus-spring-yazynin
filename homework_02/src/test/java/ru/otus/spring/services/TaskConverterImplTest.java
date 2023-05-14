package ru.otus.spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Adding new task")
@ExtendWith(MockitoExtension.class)
public class TaskConverterImplTest {

  private Task task;

  private String question;

  private List<String> answerTexts;

  private TaskConverterImpl taskConverter;

  @BeforeEach
  void setUp() {
    taskConverter = new TaskConverterImpl();
    question = "A";
    answerTexts = Arrays.asList("B", "C", "D", "E");
    List<Answer> answers = new ArrayList<>();
    for (var answerNumber = 0; answerNumber < answerTexts.size(); answerNumber++) {
      answers.add(new Answer(answerTexts.get(answerNumber), answerNumber == 0));
    }
    task = new Task(question, answers);
  }

  @DisplayName("Must convert task to string correctly")
  @Test
  void shouldCorrectConvertTaskToString() {
    assertThat(taskConverter.convertTaskToString(1, task))
        .contains(String.format("1. A%s1) B 2) C 3) D 4) E ", System.lineSeparator()));
  }

  @DisplayName("Must convert task to string correctly")
  @Test
  void shouldCorrectConvertStringToTask() {
    String taskString = "A;B;C;D;E";
    Task task = taskConverter.convertStringToTask(taskString);
    assertThat(task.getQuestion()).isEqualTo(question);
    assertThat(task.getAnswers().stream()
        .sorted(Comparator.comparing(Answer::getText))
        .map(Answer::getText).toList())
        .isEqualTo(answerTexts);
  }

  @DisplayName("Must convert task to string correctly")
  @Test
  void shouldThrowException() {
    Assertions.assertThrows(IllegalArgumentException.class, () ->
      taskConverter.convertStringToTask(";")
    );
  }
}