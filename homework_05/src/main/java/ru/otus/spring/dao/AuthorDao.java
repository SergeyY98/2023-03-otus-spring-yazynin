package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {
  int count();

  void insert(Author author);

  Author findById(long id);

  List<Author> findAll();

  void deleteById(long id);
}
