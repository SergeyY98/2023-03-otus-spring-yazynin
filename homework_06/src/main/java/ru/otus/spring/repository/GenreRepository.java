package ru.otus.spring.repository;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
  long count();

  Genre update(Genre genre);

  Optional<Genre> findById(long id);

  List<Genre> findAll();

  void deleteById(long id);
}

