package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {
  @Id
  private String id;

  private String name;

  @DBRef(lazy = true)
  private List<Author> authors;

  @DBRef(lazy = true)
  private List<Genre> genres;

  public Book(String name, List<Author> authors, List<Genre> genres) {
    this.name = name;
    this.authors = authors;
    this.genres = genres;
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<Author> getAuthors() {
    return this.authors;
  }

  public List<Genre> getGenres() {
    return this.genres;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }
}
