package ru.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.services.TaskConverterImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskDaoImplTest {

  private TaskConverterImpl taskConverter;

  @BeforeEach
  void setUp() {
    taskConverter = new TaskConverterImpl();
  }

  @DisplayName("Must conntain correct number of tasks")
  @Test
  void shouldReadTasks() {
    try {
      var file = Objects.requireNonNull(this.getClass().getClassLoader()
          .getResource("tasks.csv")).toURI();
      var lines = Files.readAllLines(Paths.get(file));
      var tasks = lines.stream()
          .map(l -> taskConverter.convertStringToTask(l))
          .toList();
      assertEquals(tasks.size(), 2);
    } catch (URISyntaxException e) {
      System.out.println("Incorrect URI");
    } catch (IOException e) {
      System.out.println("Incorrect Input");
    }
  }
}
