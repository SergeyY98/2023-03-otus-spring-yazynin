package ru.otus.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Genre;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {

  private long id;

  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 10, message = "Name should be between 2 and 10 characters")
  private String name;

  public Genre toDomainObject() {
    return new Genre(id, name);
  }

  public static GenreDto fromDomainObject(Genre genre) {
    return new GenreDto(genre.getId(), genre.getName());
  }
}
