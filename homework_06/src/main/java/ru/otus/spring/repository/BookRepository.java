package ru.otus.spring.repository;

import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
  long count();

  Book update(Book book);

  Optional<Book> findById(long id);

  List<Book> findAll();

  void deleteById(long id);
}
