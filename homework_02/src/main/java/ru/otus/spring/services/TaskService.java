package ru.otus.spring.services;

import java.util.List;
import ru.otus.spring.domain.Task;

public interface TaskService {

  List<Task> getAll();

  String getResult(int score);
}
