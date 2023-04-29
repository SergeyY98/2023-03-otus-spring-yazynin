package ru.otus.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.TaskDao;
import ru.otus.spring.domain.Task;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskDao taskDao;

  @Autowired
  public TaskServiceImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  @Override
  public List<Task> getAll() {
    return taskDao.getAll();
  }
}
