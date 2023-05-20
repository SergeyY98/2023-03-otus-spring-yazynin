package ru.otus.spring.services;

import ru.otus.spring.domain.Task;

public interface TaskConverter {
  String convertTaskToString(int taskNumber, Task task);

  Task convertStringToTask(String from);
}
