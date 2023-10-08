package ru.otus.spring.service;

import ru.otus.spring.domain.GenreMongo;

import java.util.List;

public interface GenreService {
  List<GenreMongo> findAll();

  GenreMongo findById(String id);

  void deleteById(String id);

  void save(String name);

  void count();
}
