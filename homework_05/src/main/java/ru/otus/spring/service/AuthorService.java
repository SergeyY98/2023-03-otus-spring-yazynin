package ru.otus.spring.service;

public interface AuthorService {
  void findAll();

  void findById(long id);

  void deleteById(long id);

  void insert(String firstname, String lastname);

  void update(long id, String firstname, String lastname);

  void count();
}
