package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  long count();

  Comment save(Comment comment);

  Optional<Comment> findById(long id);

  @EntityGraph(attributePaths = "book")
  @Query("select c from Comment c where exists ( " +
      "select 1 from Book b where c.book=b and b.id = :book_id)")
  List<Comment> findAllByBookId(@Param("book_id") long id);

  void deleteById(long id);
}
