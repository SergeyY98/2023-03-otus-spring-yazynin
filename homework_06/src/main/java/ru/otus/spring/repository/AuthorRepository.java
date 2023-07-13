package ru.otus.spring.repository;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
  long count();

  Author update(Author book);

  Optional<Author> findById(long id);

  List<Author> findAll();

  void deleteById(long id);
}
