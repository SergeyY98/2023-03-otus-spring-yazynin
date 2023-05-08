package ru.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskDaoImplTest {

  private List<String> taskStrings;

  @BeforeEach
  void prepareActualTasks() throws URISyntaxException, IOException {
    var file = Objects.requireNonNull(this.getClass().getClassLoader()
        .getResource("tasks.csv")).toURI();
    taskStrings = Files.readAllLines(Paths.get(file));
  }

  @DisplayName("Must contain correct number of tasks")
  @Test
  void shouldReadTaskStrings() {
    assertEquals(taskStrings.size(), 2);
  }
}
