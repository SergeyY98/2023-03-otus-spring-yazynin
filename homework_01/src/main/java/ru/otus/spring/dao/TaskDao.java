package ru.otus.spring.dao;

import ru.otus.spring.domain.Task;
import ru.otus.spring.exceptions.DataLoadingException;

import java.util.List;

public interface TaskDao {

  List<Task> getAll() throws DataLoadingException;
}
