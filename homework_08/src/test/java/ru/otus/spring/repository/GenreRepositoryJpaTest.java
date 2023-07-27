package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Genre;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class GenreRepositoryJpaTest {
  @Autowired
  private GenreRepository genreRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @DisplayName("добавлять жанр в БД")
  @Test
  void shouldInsertGenre() {
    Genre expectedGenre = new Genre("Detective");
    genreRepository.save(expectedGenre);
    Genre actualGenre = mongoTemplate.findById(expectedGenre.getId(), Genre.class);
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("обновлять жанр в БД")
  @Test
  void shouldUpdateGenre() {
    val existingId = mongoTemplate.findAll(Genre.class).get(0).getId();
    Genre expectedGenre = new Genre(existingId, "Detective");
    genreRepository.save(expectedGenre);
    Genre actualGenre = mongoTemplate.findById(expectedGenre.getId(), Genre.class);
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
  }

  @DisplayName("возвращать ожидаемый жанр по его id")
  @Test
  void shouldReturnExpectedGenreById() {
    Genre actualGenre = genreRepository.findById(mongoTemplate.findAll(Genre.class).get(0).getId())
        .orElseThrow(NoSuchElementException::new);
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(mongoTemplate.findAll(Genre.class).get(0));
  }

  @DisplayName("удалять заданный жанр по его id")
  @Test
  void shouldCorrectDeleteGenreById() {
    val existingGenre = mongoTemplate.findAll(Genre.class).get(0);
    String id = existingGenre.getId();
    assertThat(existingGenre).isNotNull();

    genreRepository.deleteById(id);
    val deletedGenre = mongoTemplate.findById(id, Genre.class);

    assertThat(deletedGenre).isNull();
  }

  @DisplayName("возвращать ожидаемый список жанров")
  @Test
  void shouldReturnExpectedGenresList() {
    List<Genre> actualGenreList = genreRepository.findAll();
    assertThat(actualGenreList.get(0)).usingRecursiveComparison().isEqualTo(mongoTemplate.findAll(Genre.class).get(0));
  }
}
