package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Author;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {

  private long id;

  private String firstname;

  private String lastname;

  public Author toDomainObject() {
    return new Author(id, firstname, lastname);
  }

  public static AuthorDto fromDomainObject(Author author) {
    return new AuthorDto(author.getId(), author.getFirstname(), author.getLastname());
  }
}
