package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreService {
  List<Genre> findAll();

  Genre findById(String id);

  void deleteById(String id);

  void save(String name);

  void count();
}
