package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Genre;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class GenreRepositoryJpaTest {
  private static final int EXPECTED_GENRE_COUNT = 10;

  private static final long EXISTING_GENRE_ID = 1;

  private static final Genre EXISTING_GENRE = new Genre(1, "genre_01");

  @Autowired
  private GenreRepository genreRepository;

  @Autowired
  private TestEntityManager em;

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
    genreRepository.save(expectedGenre);
    Genre actualGenre = em.find(Genre.class, expectedGenre.getId());
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("обновлять жанр в БД")
  @Test
  void shouldUpdateGenre() {
    Genre expectedGenre = new Genre(4, "Detective");
    genreRepository.save(expectedGenre);
    Genre actualGenre = em.find(Genre.class, expectedGenre.getId());
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("возвращать ожидаемый жанр по его id")
  @Test
  void shouldReturnExpectedGenreById() {
    Genre actualGenre = genreRepository.findById(EXISTING_GENRE.getId()).orElseThrow(NoSuchElementException::new);
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(EXISTING_GENRE);
  }

  @DisplayName("удалять заданный жанр по его id")
  @Test
  void shouldCorrectDeleteGenreById() {
    val existingGenre = em.find(Genre.class, EXISTING_GENRE_ID);
    assertThat(existingGenre).isNotNull();

    genreRepository.deleteById(EXISTING_GENRE_ID);
    val deletedGenre = em.find(Genre.class, EXISTING_GENRE_ID);

    assertThat(deletedGenre).isNull();
  }

  @DisplayName("возвращать ожидаемый список жанров")
  @Test
  void shouldReturnExpectedGenresList() {
    List<Genre> actualGenreList = genreRepository.findAll();
    assertThat(actualGenreList.get(0)).usingRecursiveComparison().isEqualTo(EXISTING_GENRE);
  }
}
