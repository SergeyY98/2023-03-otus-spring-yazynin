package ru.otus.spring.service;

import java.util.List;

public interface BookService {
  String findAll();

  String findById(String id);

  void deleteById(String id);

  void save(String name, List<String> authors, List<String> genres);

  void count();
}
