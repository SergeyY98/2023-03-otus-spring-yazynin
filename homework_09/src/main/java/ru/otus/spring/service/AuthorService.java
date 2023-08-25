package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorService {
  List<Author> findAll();

  Author findById(long id);

  void deleteById(long id);

  void insert(String firstname, String lastname);

  void save(long id, String firstname, String lastname);

  void count();
}
