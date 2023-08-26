package ru.otus.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Author;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

  private long id;

  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 10, message = "Name should be between 2 and 10 characters")
  private String firstname;

  private String lastname;

  public Author toDomainObject() {
    return new Author(id, firstname, lastname);
  }

  public static AuthorDto fromDomainObject(Author author) {
    return new AuthorDto(author.getId(), author.getFirstname(), author.getLastname());
  }
}
