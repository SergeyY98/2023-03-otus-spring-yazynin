package ru.otus.spring.services;

import java.util.List;

import ru.otus.spring.dao.TaskDaoImpl;
import ru.otus.spring.domain.Task;

public class TaskServiceImpl implements TaskService {

  private final TaskDaoImpl taskDao;

  public TaskServiceImpl(TaskDaoImpl taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public List<Task> getAll() {
    return taskDao.getAll();
  }
}
