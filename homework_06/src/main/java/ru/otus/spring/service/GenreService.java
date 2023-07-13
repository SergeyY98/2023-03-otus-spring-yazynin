package ru.otus.spring.service;

public interface GenreService {
  void findAll();

  void findById(long id);

  void deleteById(long id);

  void insert(String name);

  void update(long id, String name);

  void count();
}
