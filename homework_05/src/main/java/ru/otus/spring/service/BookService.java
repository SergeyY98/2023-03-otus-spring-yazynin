package ru.otus.spring.service;


public interface BookService {
  void findAll();

  void findById(long id);

  void deleteById(long id);

  void insert(String name);

  void count();
}
