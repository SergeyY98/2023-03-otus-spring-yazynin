package ru.otus.spring.dao;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.configs.ApplicationConfigTest;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Task;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes=ApplicationConfigTest.class)
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
        "Question1",
        List.of(new Answer("Answer11", true),
            new Answer("Answer12", false),
            new Answer("Answer13", false))
    );
    var expectedTask2 = new Task(
        "Question2",
        List.of(new Answer("Answer21", true),
            new Answer("Answer22", false),
            new Answer("Answer23", false))
    );
    return List.of(expectedTask1, expectedTask2);
  }
}