package ru.otus.spring.service;

import ru.otus.spring.domain.AuthorMongo;

import java.util.List;

public interface AuthorService {
  List<AuthorMongo> findAll();

  AuthorMongo findById(String id);

  void deleteById(String id);

  void save(String firstname, String lastname);

  void count();
}
