package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository repository;

  public Flux<Book> findAll() {
    return repository.findAll();
  }

  public Mono<Book> findById(Long id) {
    return repository.findById(id);
  }

}
