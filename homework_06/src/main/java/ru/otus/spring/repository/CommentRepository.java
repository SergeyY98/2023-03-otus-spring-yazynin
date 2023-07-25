package ru.otus.spring.repository;

import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
  Comment update(Comment comment);

  Optional<Comment> findById(long id);

  List<Comment> findAllByBookId(long book_id);

  void deleteById(long id);
}
