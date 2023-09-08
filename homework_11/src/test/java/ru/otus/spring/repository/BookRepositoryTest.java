package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.BookRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BookRepositoryTest {

  private static final List<Genre> EXISTING_GENRES = List.of(new Genre(1, "genre_01"),
      new Genre(2, "genre_02"), new Genre(3, "genre_03"));

  private static final List<Author> EXISTING_AUTHORS = List.of(new Author(1, "firstname_01","lastname_01"),
      new Author(2, "firstname_02","lastname_02"), new Author(3, "firstname_03","lastname_03"));

  @Autowired
  private BookRepository repository;

  @Test
  void shouldSetIdOnSave() {
    Mono<Book> bookMono = repository.save(new Book("book_01", EXISTING_AUTHORS, EXISTING_GENRES));

    StepVerifier
        .create(bookMono)
        .assertNext(book -> assertNotNull(book.getId()))
        .expectComplete()
        .verify();
  }
}