package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookDao {
  int count();

  void insert(Book book);

  Book findById(long id);

  List<Book> findAll();

  void deleteById(long id);
}
