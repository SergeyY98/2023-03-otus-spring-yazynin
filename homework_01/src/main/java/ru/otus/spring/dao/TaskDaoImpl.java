package ru.otus.spring.dao;

import ru.otus.spring.domain.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskDaoImpl implements TaskDao {

  private final Map<String, Task> tasks;

  public TaskDaoImpl() {
    tasks = new HashMap<>();
  }

  @Override
  public List<Task> getAll() {
    return tasks.values().stream().map(Task::copy).collect(Collectors.toList());
  }

  @Override
  public void save(final Task task) {
    tasks.put(task.getId(), task);
  }
}
