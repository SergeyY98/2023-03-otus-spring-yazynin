package ru.otus.spring.service;

import java.util.List;

public interface BookService {
  void findAll();

  void findById(long id);

  void deleteById(long id);

  void update(long id, String name, List<Long> authors, List<Long> genres);

  void count();
}
