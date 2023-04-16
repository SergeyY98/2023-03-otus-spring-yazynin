package ru.otus.spring.services;

import java.util.List;
import ru.otus.spring.domain.Task;
import ru.otus.spring.exceptions.DataLoadingException;

public interface TaskService {

  List<Task> getAll() throws DataLoadingException;
}
