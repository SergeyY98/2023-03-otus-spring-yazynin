package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentService {

  List<Comment> findAllByBookId(long id);

  void deleteById(long id);
}
