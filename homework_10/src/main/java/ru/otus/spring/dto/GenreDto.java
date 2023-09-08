package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Genre;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

  private long id;

  private String name;

  public Genre toDomainObject() {
    return new Genre(id, name);
  }

  public static GenreDto fromDomainObject(Genre genre) {
    return new GenreDto(genre.getId(), genre.getName());
  }
}
