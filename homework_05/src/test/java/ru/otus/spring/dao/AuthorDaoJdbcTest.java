package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({AuthorDaoJdbc.class})
public class AuthorDaoJdbcTest {
  private static final int EXPECTED_AUTHOR_COUNT = 3;
  private static final int EXISTING_AUTHOR_ID = 1;
  private static final Author EXISTING_AUTHOR = new Author(1, "Frank","Herbert");

  @Autowired
  private AuthorDaoJdbc authorDao;

  @BeforeTransaction
  void beforeTransaction(){
    System.out.println("beforeTransaction");
  }

  @AfterTransaction
  void afterTransaction(){
    System.out.println("afterTransaction");
  }

  @DisplayName("возвращать ожидаемое количество авторов в БД")
  @Test
  void shouldReturnExpectedPersonCount() {
    int actualBooksCount = authorDao.count();
    assertThat(actualBooksCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
  }

  //@Rollback(value = false)
  //@Commit
  @DisplayName("добавлять книгу в БД")
  @Test
  void shouldInsertPerson() {
    Author expectedAuthor = new Author(4, "Philip", "Dick");
    authorDao.insert(expectedAuthor);
    Author actualPerson = authorDao.findById(expectedAuthor.getId());
    assertThat(actualPerson).usingRecursiveComparison().isEqualTo(expectedAuthor);
  }

  @DisplayName("возвращать ожидаемую книгу по ее id")
  @Test
  void shouldReturnExpectedPersonById() {
    Author actualBook = authorDao.findById(EXISTING_AUTHOR.getId());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXISTING_AUTHOR);
  }

  @DisplayName("возвращать ожидаемый список авторов")
  @Test
  void shouldReturnExpectedPersonsList() {
    List<Author> actualBookList = authorDao.findAll();
    assertThat(actualBookList)
        .contains(EXISTING_AUTHOR);
  }
}
