package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class BookMongo {
  @Id
  private String id;

  private String name;

  @DBRef(lazy = true)
  private List<AuthorMongo> authors;

  @DBRef(lazy = true)
  private List<GenreMongo> genres;

  public BookMongo(String name, List<AuthorMongo> authors, List<GenreMongo> genres) {
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

  public List<AuthorMongo> getAuthors() {
    return this.authors;
  }

  public List<GenreMongo> getGenres() {
    return this.genres;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAuthors(List<AuthorMongo> authors) {
    this.authors = authors;
  }

  public void setGenres(List<GenreMongo> genres) {
    this.genres = genres;
  }
}
