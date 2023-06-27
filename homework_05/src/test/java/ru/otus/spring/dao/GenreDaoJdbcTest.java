package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({GenreDaoJdbc.class})
public class GenreDaoJdbcTest {
  private static final int EXPECTED_GENRE_COUNT = 3;
  private static final int EXISTING_GENRE_ID = 1;
  private static final Genre EXISTING_GENRE = new Genre(1, "Science fiction");

  @Autowired
  private GenreDaoJdbc GenreDao;

  @BeforeTransaction
  void beforeTransaction(){
    System.out.println("beforeTransaction");
  }

  @AfterTransaction
  void afterTransaction(){
    System.out.println("afterTransaction");
  }

  @DisplayName("возвращать ожидаемое количество жанров в БД")
  @Test
  void shouldReturnExpectedPersonCount() {
    int actualGenresCount = GenreDao.count();
    assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRE_COUNT);
  }

  @DisplayName("добавлять книгу в БД")
  @Test
  void shouldInsertPerson() {
    Genre expectedGenre = new Genre(4, "Detective");
    GenreDao.insert(expectedGenre);
    Genre actualGenre = GenreDao.findById(expectedGenre.getId());
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("возвращать ожидаемую книгу по ее id")
  @Test
  void shouldReturnExpectedPersonById() {
    Genre actualGenre = GenreDao.findById(EXISTING_GENRE.getId());
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(EXISTING_GENRE);
  }

  @DisplayName("возвращать ожидаемый список жанров")
  @Test
  void shouldReturnExpectedPersonsList() {
    List<Genre> actualGenreList = GenreDao.findAll();
    assertThat(actualGenreList)
        .contains(EXISTING_GENRE);
  }
}
