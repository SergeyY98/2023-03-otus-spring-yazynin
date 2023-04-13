package ru.otus.spring.services;

import java.util.stream.IntStream;

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
    outputTasks();
  }

  private void outputTasks() {
    var tasks = tasksService.getAll();
    IntStream.range(1, tasks.size() + 1)
        .mapToObj(k -> taskConverter.convertTaskToString(k, tasks.get(k - 1)))
        .forEach(ioService::outputString);
  }
}
