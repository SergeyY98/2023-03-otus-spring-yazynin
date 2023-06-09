package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  private long id;

  private String name;

  private List<Author> authors;

  private List<Genre> genres;

  public Book(String name, List<Author> authors, List<Genre> genres) {
    this.name = name;
    this.authors = authors;
    this.genres = genres;
  }
}
