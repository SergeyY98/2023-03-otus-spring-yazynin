package ru.otus.spring.dao;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Task;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TaskDaoImplTest {

  @Autowired
  private TaskDao taskDao;

  @DisplayName("Should return expected tasks")
  @Test
  void shouldReturnExpectedTasks() {
    var expectedTasks = prepareExpectedTasks();

    var actualTasks = taskDao.getAll();
    assertThat(actualTasks)
        .usingRecursiveFieldByFieldElementComparator(
            RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id")
                .withIgnoreCollectionOrder(true)
                .build())
        .containsExactlyInAnyOrderElementsOf(expectedTasks);
  }

  private List<Task> prepareExpectedTasks() {
    var expectedTask1 = new Task(
        "Вопрос1",
        List.of(new Answer("Ответ11", true),
            new Answer("Ответ12", false),
            new Answer("Ответ13", false))
    );
    var expectedTask2 = new Task(
        "Вопрос2",
        List.of(new Answer("Ответ21", true),
            new Answer("Ответ22", false),
            new Answer("Ответ23", false))
    );
    return List.of(expectedTask1, expectedTask2);
  }
}