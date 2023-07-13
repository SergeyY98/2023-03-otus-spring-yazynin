package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({GenreRepositoryJpa.class})
public class GenreRepositoryJpaTest {
  private static final int EXPECTED_GENRE_COUNT = 10;

  private static final int EXISTING_GENRE_ID = 1;

  private static final Genre EXISTING_GENRE = new Genre(1, "genre_01");

  @Autowired
  private GenreRepository genreRepository;

  @DisplayName("возвращать ожидаемое количество жанров в БД")
  @Test
  void shouldReturnExpectedGenreCount() {
    long actualGenresCount = genreRepository.count();
    assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRE_COUNT);
  }

  @DisplayName("добавлять жанр в БД")
  @Test
  void shouldInsertGenre() {
    Genre expectedGenre = new Genre(5, "Detective");
    genreRepository.update(expectedGenre);
    Genre actualGenre = genreRepository.findById(expectedGenre.getId()).get();
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("обновлять жанр в БД")
  @Test
  void shouldUpdateGenre() {
    Genre expectedGenre = new Genre(4, "Detective");
    genreRepository.update(expectedGenre);
    Genre actualGenre = genreRepository.findById(expectedGenre.getId()).get();
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("возвращать ожидаемый жанр по его id")
  @Test
  void shouldReturnExpectedGenreById() {
    Genre actualGenre = genreRepository.findById(EXISTING_GENRE.getId()).get();
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(EXISTING_GENRE);
  }

  @DisplayName("удалять заданный жанр по его id")
  @Test
  void shouldCorrectDeleteGenreById() {
    assertThatCode(() -> genreRepository.findById(EXISTING_GENRE_ID).get())
        .doesNotThrowAnyException();

    genreRepository.deleteById(EXISTING_GENRE_ID);

    assertThatThrownBy(() -> genreRepository.findById(EXISTING_GENRE_ID).get())
        .isInstanceOf(NoSuchElementException.class);
  }

  @DisplayName("возвращать ожидаемый список жанров")
  @Test
  void shouldReturnExpectedGenresList() {
    List<Genre> actualGenreList = genreRepository.findAll();
    assertThat(actualGenreList)
        .contains(EXISTING_GENRE);
  }
}
