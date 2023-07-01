package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Genre;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import({GenreDaoJdbc.class})
public class GenreDaoJdbcTest {
  private static final int EXPECTED_GENRE_COUNT = 4;
  private static final int EXISTING_GENRE_ID = 4;
  private static final Genre EXISTING_GENRE = new Genre(1, "Science fiction");

  @Autowired
  private GenreDao genreDao;

  @DisplayName("возвращать ожидаемое количество жанров в БД")
  @Test
  void shouldReturnExpectedGenreCount() {
    int actualGenresCount = genreDao.count();
    assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRE_COUNT);
  }

  @DisplayName("добавлять жанр в БД")
  @Test
  void shouldInsertGenre() {
    Genre expectedGenre = new Genre(5, "Detective");
    genreDao.insert(expectedGenre);
    Genre actualGenre = genreDao.findById(expectedGenre.getId());
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("возвращать ожидаемый жанр по его id")
  @Test
  void shouldReturnExpectedGenreById() {
    Genre actualGenre = genreDao.findById(EXISTING_GENRE.getId());
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(EXISTING_GENRE);
  }

  @DisplayName("удалять заданный жанр по его id")
  @Test
  void shouldCorrectDeleteGenreById() {
    assertThatCode(() -> genreDao.findById(EXISTING_GENRE_ID))
        .doesNotThrowAnyException();

    genreDao.deleteById(EXISTING_GENRE_ID);

    assertThatThrownBy(() -> genreDao.findById(EXISTING_GENRE_ID))
        .isInstanceOf(EmptyResultDataAccessException.class);
  }

  @DisplayName("возвращать ожидаемый список жанров")
  @Test
  void shouldReturnExpectedGenresList() {
    List<Genre> actualGenreList = genreDao.findAll();
    assertThat(actualGenreList)
        .contains(EXISTING_GENRE);
  }
}
