package ru.otus.spring.dao;

import ru.otus.spring.domain.Genre;

import java.util.List;

public interface GenreDao {
  int count();

  void insert(Genre genre);

  void update(Genre genre);

  Genre findById(long genre_id);

  List<Genre> findAll();

  List<Genre> findAllWithRelations();

  void deleteById(long genre_id);
}
