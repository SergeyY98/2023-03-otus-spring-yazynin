package ru.otus.spring.services;

import java.util.List;

import ru.otus.spring.dao.TaskDao;
import ru.otus.spring.domain.Task;
import ru.otus.spring.exceptions.DataLoadingException;

public class TaskServiceImpl implements TaskService {

  private final TaskDao taskDao;

  public TaskServiceImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public List<Task> getAll() throws DataLoadingException {
    return taskDao.getAll();
  }
}
