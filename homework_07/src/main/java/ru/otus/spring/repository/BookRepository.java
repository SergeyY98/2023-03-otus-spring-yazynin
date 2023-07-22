package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
  long count();

  Book save(Book book);

  Optional<Book> findById(long id);

  List<Book> findAll();

  void deleteAll();

  void deleteById(long id);
}
