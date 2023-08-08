package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorService {
  List<Author> findAll();

  Author findById(String id);

  void deleteById(String id);

  void save(String firstname, String lastname);

  void count();
}
