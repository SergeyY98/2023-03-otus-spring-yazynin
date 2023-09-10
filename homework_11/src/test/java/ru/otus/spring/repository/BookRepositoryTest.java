package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BookRepositoryTest {

  private static final List<Author> EXISTING_AUTHORS = List.of(new Author(1, "firstname_01","lastname_01"),
      new Author(2, "firstname_02","lastname_02"), new Author(3, "firstname_03","lastname_03"));

  @Autowired
  private BookRepository repository;

  @Test
  void shouldSetIdOnSave() {
    Mono<Book> bookMono = repository.save(new Book("book_01", EXISTING_AUTHORS));

    StepVerifier
        .create(bookMono)
        .assertNext(book -> assertNotNull(book.getId()))
        .expectComplete()
        .verify();
  }
}