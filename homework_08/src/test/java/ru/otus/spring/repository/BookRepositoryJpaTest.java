package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно")
@DataMongoTest
public class BookRepositoryJpaTest {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @DisplayName("возвращать ожидаемую книгу по ее id")
  @Test
  void shouldReturnExpectedBookById() {
    Book actualBook = bookRepository.findById(mongoTemplate.findAll(Book.class).get(0).getId())
        .orElseThrow(NoSuchElementException::new);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(mongoTemplate.findAll(Book.class).get(0));
  }

  @DisplayName("добавлять книгу в БД")
  @Test
  void shouldInsertBook() {
    Book expectedBook = new Book("Roadside picnic", null, null);
    bookRepository.save(expectedBook);
    Book actualBook = mongoTemplate.findById(expectedBook.getId(), Book.class);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @DisplayName("обновлять книгу в БД")
  @Test
  void shouldUpdateBook() {
    val existingId = mongoTemplate.findAll(Book.class).get(0).getId();
    Book expectedBook = new Book(existingId, "Roadside picnic", null, null);
    bookRepository.save(expectedBook);
    Book actualBook = mongoTemplate.findById(expectedBook.getId(), Book.class);
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @DisplayName("удалять заданную книгу по ее id")
  @Test
  void shouldCorrectDeleteBookById() {
    val existingBook = mongoTemplate.findAll(Book.class).get(0);
    String id = existingBook.getId();
    assertThat(existingBook).isNotNull();

    bookRepository.deleteById(id);
    val deletedBook = mongoTemplate.findById(id, Book.class);

    assertThat(deletedBook).isNull();
  }

  @DisplayName("возвращать ожидаемый список книг")
  @Test
  void shouldContainExpectedBookInList() {
    val existingBook = mongoTemplate.findAll(Book.class).get(0);
    List<Book> actualBookList = bookRepository.findAll();
    assertThat(actualBookList.get(0))
        .usingRecursiveComparison().isEqualTo(existingBook);
  }
}