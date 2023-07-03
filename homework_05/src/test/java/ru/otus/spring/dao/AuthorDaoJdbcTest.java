package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({AuthorDaoJdbc.class})
public class AuthorDaoJdbcTest {
  private static final int EXPECTED_AUTHOR_COUNT = 4;
  private static final int EXISTING_AUTHOR_ID = 4;
  private static final Author EXISTING_AUTHOR = new Author(1, "Frank","Herbert");

  @Autowired
  private AuthorDao authorDao;

  @DisplayName("возвращать ожидаемое количество авторов в БД")
  @Test
  void shouldReturnExpectedAuthorCount() {
    int actualBooksCount = authorDao.count();
    assertThat(actualBooksCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
  }

  @DisplayName("добавлять автора в БД")
  @Test
  void shouldInsertAuthor() {
    Author expectedAuthor = new Author(5, "Philip", "Dick");
    authorDao.insert(expectedAuthor);
    Author actualAuthor = authorDao.findById(expectedAuthor.getId());
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("обновлять автора в БД")
  @Test
  void shouldUpdateAuthor() {
    Author expectedAuthor = new Author(4, "Philip", "Dick");
    authorDao.update(expectedAuthor);
    Author actualAuthor = authorDao.findById(expectedAuthor.getId());
    assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("возвращать ожидаемого автора по его id")
  @Test
  void shouldReturnExpectedAuthorById() {
    Author actualBook = authorDao.findById(EXISTING_AUTHOR.getId());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXISTING_AUTHOR);
  }

  @DisplayName("удалять заданного автора по его id")
  @Test
  void shouldCorrectDeleteGenreById() {
    assertThatCode(() -> authorDao.findById(EXISTING_AUTHOR_ID))
        .doesNotThrowAnyException();

    authorDao.deleteById(EXISTING_AUTHOR_ID);

    assertThatThrownBy(() -> authorDao.findById(EXISTING_AUTHOR_ID))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }

  @DisplayName("возвращать ожидаемый список авторов")
  @Test
  void shouldReturnExpectedAuthorsList() {
    List<Author> actualBookList = authorDao.findAll();
    assertThat(actualBookList)
        .contains(EXISTING_AUTHOR);
  }
}
