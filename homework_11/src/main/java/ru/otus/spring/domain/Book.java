package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Table("books")
public class Book {
  @Id
  private long id;

  private String name;

  private List<Author> authors  = new ArrayList<>();

  private List<Genre> genres = new ArrayList<>();

  public Book(String name, List<Author> authors, List<Genre> genres) {
    this.name = name;
    this.authors = authors;
    this.genres = genres;
  }

  public long getId() {
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

  public void setId(long id) {
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

  public static Mono<Book> fromRows(List<Map<String, Object>> rows) {
    return Mono.just(new Book(
        Long.parseLong(rows.get(0).get("b_id").toString()),
        rows.get(0).get("b_name").toString(),
        rows.stream()
            .map(Author::fromRow)
            .filter(Objects::nonNull)
            .toList(),
        rows.stream()
            .map(Genre::fromRow)
            .filter(Objects::nonNull)
            .toList()));
  }
}
