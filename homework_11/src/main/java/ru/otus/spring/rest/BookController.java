package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.BookRepository;

import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

  private final BookRepository bookRepository;

  @GetMapping
  public Flux<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Book>> getBookById(@PathVariable("id") long id) {
    return bookRepository.findById(id)
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
  }

  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> deleteBook(@PathVariable("id") long id) {
    return bookRepository.findById(id)
        .flatMap(bookRepository::delete)
        .map(v -> new ResponseEntity<Void>(HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping
  public Mono<Book> saveBook(@RequestBody Book book) {
    return bookRepository.save(book);
  }

  @PutMapping
  public Mono<Book> updateBook(@RequestBody Book book) {
    return bookRepository.update(book);
  }
}
