package ru.otus.spring.dao;

import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.domain.Task;
import ru.otus.spring.services.TaskConverterImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskDaoImpl implements TaskDao {

  private final String fileName;

  private final TaskConverterImpl taskConverter;

  public TaskDaoImpl(String fileName, TaskConverterImpl taskConverter) {
    this.fileName = fileName;
    this.taskConverter = taskConverter;
  }

  @Override
  public List<Task> getAll() {
    var resource = new ClassPathResource(fileName);
    var tasks = new ArrayList<Task>();
    try {
      InputStream inputStream = resource.getInputStream();
      Scanner input = new Scanner(inputStream);
      while (input.hasNextLine()) {
        var task = taskConverter.convertStringToTask(input.nextLine());
        tasks.add(task);
      }
    } catch (IOException e) {
      System.out.println("Ошибка при чтении ресурса");
    }
    return tasks;
  }
}
