package ru.otus.spring.service;

import java.util.List;

public interface BookService {
  String findAll();

  String findById(long id);

  void deleteById(long id);

  void save(long id, String name, List<Long> authors, List<Long> genres);

  void count();
}
