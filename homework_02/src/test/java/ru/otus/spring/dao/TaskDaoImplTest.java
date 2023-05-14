package ru.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Task;
import ru.otus.spring.services.TaskConverterImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskDaoImplTest {

  private List<Task> tasks;

  private List<String> taskList1;

  private List<String> taskList2;

  @BeforeEach
  void prepareActualTasks() throws URISyntaxException, IOException {
    var file = Objects.requireNonNull(this.getClass().getClassLoader()
        .getResource("tasks.csv")).toURI();
    var writer = Files.newBufferedWriter(Paths.get(file));
    var taskString1 = "Question1;Answer11;Answer12;Answer13.";
    var taskString2 = "Question1;Answer11;Answer12;Answer13.";
    taskList1 = Arrays.asList(taskString1.split(";"));
    taskList2 = Arrays.asList(taskString2.split(";"));
    writer.write(taskString1);
    writer.newLine();
    writer.write(taskString2);
    writer.close();
    TaskConverterImpl taskConverter = new TaskConverterImpl();
    var taskDao = new TaskDaoImpl("tasks.csv", taskConverter);
    tasks = taskDao.getAll();
  }

  @DisplayName("Must contain correct number of tasks")
  @Test
  void shouldReadTaskStrings() {
    List<String> answerTexts1 = taskList1.subList(1, taskList1.size());
    List<String> answerTexts2 = taskList2.subList(1, taskList2.size());
    assertEquals(tasks.size(), 2);
    assertThat(tasks.get(0).getQuestion()).isEqualTo(taskList1.get(0));
    assertThat(tasks.get(1).getQuestion()).isEqualTo(taskList2.get(0));
    assertThat(tasks.get(0).getAnswers().stream()
        .sorted(Comparator.comparing(Answer::getText))
        .map(Answer::getText).toList())
        .isEqualTo(answerTexts1);
    assertThat(tasks.get(1).getAnswers().stream()
        .sorted(Comparator.comparing(Answer::getText))
        .map(Answer::getText).toList())
        .isEqualTo(answerTexts2);
  }
}
