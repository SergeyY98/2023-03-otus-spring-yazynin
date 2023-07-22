package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreService {
  List<Genre> findAll();

  Genre findById(long id);

  void deleteById(long id);

  void insert(String name);

  void save(long id, String name);

  void count();
}
