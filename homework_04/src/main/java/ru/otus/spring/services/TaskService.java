package ru.otus.spring.services;

import ru.otus.spring.domain.Task;

import java.util.List;

public interface TaskService {

  List<Task> getAll();

  int getTaskResult(int index, Task task);
}
