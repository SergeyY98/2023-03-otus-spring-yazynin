package ru.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.config.DaoConfig;
import ru.otus.spring.domain.Task;
import ru.otus.spring.exceptions.DataLoadingException;
import ru.otus.spring.services.TaskConverterImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=DaoConfig.class)
public class TaskDaoImplTest {

  private TaskConverterImpl taskConverter;

  @Autowired
  private String fileName;

  @Autowired
  private int passedScore;

  @BeforeEach
  void setUp() {
    taskConverter = new TaskConverterImpl();
  }

  @DisplayName("Must display tasks")
  @Test
  void getAllTest() {
    var resource = new ClassPathResource(fileName);
    var tasks = new ArrayList<Task>();
    try (Scanner scanner = new Scanner(resource.getInputStream())) {
      while (scanner.hasNextLine()) {
        var task = taskConverter.convertStringToTask(scanner.nextLine());
        tasks.add(task);
      }
    } catch (IOException e) {
      throw new DataLoadingException("Resource reading error", e.getCause());
    }
    assertEquals(tasks.size(), 2);
    assertEquals(passedScore, 3);
  }
}
