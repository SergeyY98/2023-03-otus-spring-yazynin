package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
  private long id;

  private String firstname;

  private String lastname;

  public Author(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }
}
