package ru.otus.spring.dao;

import ru.otus.spring.domain.Author;

import java.util.List;

public interface AuthorDao {
  int count();

  void insert(Author author);

  void update(Author author);

  Author findById(long id);

  List<Author> findAll();

  List<Author> findAllWithRelations();

  void deleteById(long id);
}
