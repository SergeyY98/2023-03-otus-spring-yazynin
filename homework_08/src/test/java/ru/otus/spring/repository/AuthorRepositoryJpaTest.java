package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class AuthorRepositoryJpaTest {
  private static final Author EXISTING_AUTHOR = new Author("firstname_01", "lastname_01");

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @DisplayName("добавлять автора в БД")
  @Test
  void shouldInsertAuthor() {
    Author expectedAuthor = new Author("Philip", "Dick");
    authorRepository.save(expectedAuthor);
    Author actualAuthor = mongoTemplate.findById(expectedAuthor.getId(), Author.class);
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("обновлять автора в БД")
  @Test
  void shouldUpdateAuthor() {
    val existingId = mongoTemplate.findAll(Author.class).get(0).getId();
    Author expectedAuthor = new Author(existingId, "Philip", "Dick");
    authorRepository.save(expectedAuthor);
    Author actualAuthor = mongoTemplate.findById(expectedAuthor.getId(), Author.class);
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("возвращать ожидаемого автора по его id")
  @Test
  void shouldReturnExpectedAuthorById() {
    Author actualAuthor = authorRepository.findById(mongoTemplate.findAll(Author.class).get(0).getId())
        .orElseThrow(NoSuchElementException::new);
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(mongoTemplate.findAll(Author.class).get(0));
  }

  @DisplayName("удалять заданного автора по его id")
  @Test
  void shouldCorrectDeleteAuthorById() {
    val existingAuthor = mongoTemplate.findAll(Author.class).get(0);
    String id = existingAuthor.getId();
    assertThat(existingAuthor).isNotNull();

    authorRepository.deleteById(id);
    val deletedGenre = mongoTemplate.findById(id, Author.class);

    assertThat(deletedGenre).isNull();
  }

  @DisplayName("возвращать ожидаемый список авторов")
  @Test
  void shouldReturnExpectedAuthorsList() {
    List<Author> actualAuthorList = authorRepository.findAll();
    assertThat(actualAuthorList.get(0)).usingRecursiveComparison()
        .isEqualTo(mongoTemplate.findAll(Author.class).get(0));
  }
}
