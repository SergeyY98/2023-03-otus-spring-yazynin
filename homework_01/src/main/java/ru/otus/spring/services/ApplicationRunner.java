package ru.otus.spring.services;

import java.util.stream.IntStream;
import ru.otus.spring.domain.Task;

public class ApplicationRunner {
  private final IOService ioService;

  private final TaskService tasksService;

  private final TaskConverter taskConverter;

  public ApplicationRunner(IOService ioService, TaskService tasksService,
                           TaskConverter taskConverter) {
    this.ioService = ioService;
    this.tasksService = tasksService;
    this.taskConverter = taskConverter;
  }

  public void run() {
    inputTasks();
    outputTasks();
  }

  private void inputTasks() {
    String line;
    while ((line = ioService.readString()) != "") {
      Task task = taskConverter.convertStringToTask(line);
      tasksService.save(task);
    }
  }

  private void outputTasks() {
    var tasks = tasksService.getAll();
    IntStream.range(1, tasks.size() + 1)
        .mapToObj(k -> taskConverter.convertTaskToString(k, tasks.get(k - 1)))
        .forEach(ioService::outputString);
  }
}
