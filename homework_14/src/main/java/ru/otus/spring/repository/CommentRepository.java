package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.domain.CommentMongo;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentMongo, String> {

  @Query("{'book._id' : ?0 }")
  List<CommentMongo> findAllByBookId(@Param("book_id") String id);

  @Query(value = "{'book._id' : ?0 }", delete = true)
  List<CommentMongo> deleteAllByBookId(@Param("book_id") String id);
}
