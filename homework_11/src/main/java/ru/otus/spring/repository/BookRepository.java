package ru.otus.spring.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;

public interface BookRepository {
  Flux<Book> findAll();

  Mono<Book> findById(long id);

  Mono<Book> save(Book book);

  Mono<Book> update(Book book);

  Mono<Void> delete(Book book);
}
