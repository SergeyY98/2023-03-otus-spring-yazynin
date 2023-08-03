package ru.otus.spring.repository;

public interface BookRepositoryCustom {
  void removeAuthorsArrayElementsById(String id);

  void removeGenresArrayElementsById(String id);
}
