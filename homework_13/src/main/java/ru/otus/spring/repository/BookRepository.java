package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
  @PostFilter("hasPermission(filterObject, 'READ')")
  List<Book> findAll();

  @PostAuthorize("hasPermission(returnObject, 'READ')")
  Book getById(long id);

  @PreAuthorize("hasPermission(#book, 'DELETE')")
  void delete(@Param("book") Book book);
}
