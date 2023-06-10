package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.domain.Task;
import ru.otus.spring.exceptions.DataLoadingException;
import ru.otus.spring.services.TaskConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class TaskDaoImpl implements TaskDao {

  private final AppProps appProps;

  private final TaskConverter taskConverter;

  @Autowired
  public TaskDaoImpl(AppProps appProps, TaskConverter taskConverter) {
    this.appProps = appProps;
    this.taskConverter = taskConverter;
  }

  @Override
  public List<Task> getAll() {
    var tasks = new ArrayList<Task>();
    try (Scanner scanner = new Scanner(
        new ClassPathResource(appProps.getFileName()).getInputStream())) {
      while (scanner.hasNextLine()) {
        var task = taskConverter.convertStringToTask(scanner.nextLine());
        tasks.add(task);
      }
    } catch (IOException e) {
      throw new DataLoadingException("Resource reading error", e.getCause());
    }
    return tasks;
  }
}

