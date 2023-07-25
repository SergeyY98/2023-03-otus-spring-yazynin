package ru.otus.spring.service;

public interface CommentService {

  String findAllByBookId(long id);

  void deleteById(long id);
}
