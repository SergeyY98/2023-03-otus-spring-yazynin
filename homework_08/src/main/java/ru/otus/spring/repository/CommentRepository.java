package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

  @Query("{'book._id' : ?0 }")
  List<Comment> findAllByBookId(@Param("book_id") String id);

  @Query(value = "{'book._id' : ?0 }", delete = true)
  List<Comment> deleteAllByBookId(@Param("book_id") String id);
}
