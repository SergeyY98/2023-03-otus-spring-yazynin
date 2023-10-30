package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("select c from Comment c where exists ( " +
      "select 1 from Book b where c.book=b and b.id = :book_id)")
  List<Comment> findAllByBookId(@Param("book_id") long id);
}
