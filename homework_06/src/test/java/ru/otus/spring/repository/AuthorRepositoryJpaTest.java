package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({AuthorRepositoryJpa.class})
public class AuthorRepositoryJpaTest {
  private static final int EXPECTED_AUTHOR_COUNT = 10;

  private static final int EXISTING_AUTHOR_ID = 1;

  private static final Author EXISTING_AUTHOR = new Author(1, "firstname_01", "lastname_01");

  @Autowired
  private AuthorRepository authorDao;

  @DisplayName("возвращать ожидаемое количество авторов в БД")
  @Test
  void shouldReturnExpectedAuthorCount() {
    long actualBooksCount = authorDao.count();
    assertThat(actualBooksCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
  }

  @DisplayName("добавлять автора в БД")
  @Test
  void shouldInsertAuthor() {
    Author expectedAuthor = new Author(5, "Philip", "Dick");
    authorDao.update(expectedAuthor);
    Author actualAuthor = authorDao.findById(expectedAuthor.getId()).get();
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("обновлять автора в БД")
  @Test
  void shouldUpdateAuthor() {
    Author expectedAuthor = new Author(4, "Philip", "Dick");
    authorDao.update(expectedAuthor);
    Author actualAuthor = authorDao.findById(expectedAuthor.getId()).get();
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("возвращать ожидаемого автора по его id")
  @Test
  void shouldReturnExpectedAuthorById() {
    Author actualBook = authorDao.findById(EXISTING_AUTHOR.getId()).get();
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXISTING_AUTHOR);
  }

  @DisplayName("удалять заданного автора по его id")
  @Test
  void shouldCorrectDeleteGenreById() {
    assertThatCode(() -> authorDao.findById(EXISTING_AUTHOR_ID).get())
        .doesNotThrowAnyException();

    authorDao.deleteById(EXISTING_AUTHOR_ID);

    assertThatThrownBy(() -> authorDao.findById(EXISTING_AUTHOR_ID).get())
        .isInstanceOf(NoSuchElementException.class);
  }

  @DisplayName("возвращать ожидаемый список авторов")
  @Test
  void shouldReturnExpectedAuthorsList() {
    List<Author> actualBookList = authorDao.findAll();
    assertThat(actualBookList)
        .contains(EXISTING_AUTHOR);
  }
}
