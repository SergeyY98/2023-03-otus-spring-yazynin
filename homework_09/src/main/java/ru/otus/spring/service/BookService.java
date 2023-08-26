package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {
  List<Book> findAll();

  Book findById(long id);

  void deleteById(long id);

  void save(Book book);
}
