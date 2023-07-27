package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

  @Query("select c from Comment c where exists ( " +
      "select 1 from Book b where c.book=b and b.name = :book_name)")
  List<Comment> findAllByBookName(@Param("book_name") String name);
}
