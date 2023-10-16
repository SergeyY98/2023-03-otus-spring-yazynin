package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.domain.Book;

import java.util.List;

@RepositoryRestResource(path = "book")
public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findAll();

  @RestResource(path = "books", rel = "ids")
  Book getById(long id);

  void delete(@Param("book") Book book);
}
