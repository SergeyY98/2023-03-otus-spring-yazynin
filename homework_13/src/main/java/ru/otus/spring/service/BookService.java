package ru.otus.spring.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.spring.domain.Book;

import java.util.List;

public interface BookService {
  @PostFilter("hasPermission(filterObject, 'READ')")
  List<Book> findAll();

  @PostAuthorize("hasPermission(returnObject, 'READ')")
  Book findById(long id);

  @PreAuthorize("hasPermission(#book, 'DELETE')")
  void deleteById(long id);

  void save(Book book);
}
