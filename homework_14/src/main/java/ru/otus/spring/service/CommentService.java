package ru.otus.spring.service;

public interface CommentService {
  String findAll();

  String findAllByBookId(String id);

  void deleteById(String id);
}
