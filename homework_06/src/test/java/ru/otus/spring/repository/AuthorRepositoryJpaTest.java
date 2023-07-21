package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AuthorRepositoryJpa.class})
public class AuthorRepositoryJpaTest {
  private static final int EXPECTED_AUTHOR_COUNT = 10;

  private static final long EXISTING_AUTHOR_ID = 1;

  private static final Author EXISTING_AUTHOR = new Author(EXISTING_AUTHOR_ID, "firstname_01", "lastname_01");

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private TestEntityManager em;

  @DisplayName("возвращать ожидаемое количество авторов в БД")
  @Test
  void shouldReturnExpectedAuthorCount() {
    long actualAuthorsCount = authorRepository.count();
    assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
  }

  @DisplayName("добавлять автора в БД")
  @Test
  void shouldInsertAuthor() {
    Author expectedAuthor = new Author(5, "Philip", "Dick");
    authorRepository.update(expectedAuthor);
    Author actualAuthor = em.find(Author.class, expectedAuthor.getId());
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("обновлять автора в БД")
  @Test
  void shouldUpdateAuthor() {
    Author expectedAuthor = new Author(4, "Philip", "Dick");
    authorRepository.update(expectedAuthor);
    Author actualAuthor = em.find(Author.class, expectedAuthor.getId());
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("возвращать ожидаемого автора по его id")
  @Test
  void shouldReturnExpectedAuthorById() {
    Author actualAuthor = authorRepository.findById(EXISTING_AUTHOR_ID).orElseThrow(NoSuchElementException::new);
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(EXISTING_AUTHOR);
  }

  @DisplayName("удалять заданного автора по его id")
  @Test
  void shouldCorrectDeleteGenreById() {
    val existingAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
    assertThat(existingAuthor).isNotNull();

    authorRepository.deleteById(EXISTING_AUTHOR_ID);
    val deletedAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);

    assertThat(deletedAuthor).isNull();
  }

  @DisplayName("возвращать ожидаемый список авторов")
  @Test
  void shouldReturnExpectedAuthorsList() {
    List<Author> actualAuthorList = authorRepository.findAll();
    assertThat(actualAuthorList.get(0)).usingRecursiveComparison().isEqualTo(EXISTING_AUTHOR);
  }
}
