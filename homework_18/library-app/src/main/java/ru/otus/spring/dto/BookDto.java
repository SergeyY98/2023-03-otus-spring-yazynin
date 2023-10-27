package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Book;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

  private long id;

  private String name;

  private List<AuthorDto> authors;

  private List<GenreDto> genres;

  public Book toDomainObject() {
    return new Book(id, name,
        authors.stream().map(AuthorDto::toDomainObject).collect(toList()),
        genres.stream().map(GenreDto::toDomainObject).collect(toList()));
  }

  public static BookDto fromDomainObject(Book book) {
    return new BookDto(book.getId(), book.getName(),
        book.getAuthors().stream().map(AuthorDto::fromDomainObject).collect(toList()),
        book.getGenres().stream().map(GenreDto::fromDomainObject).collect(toList()));
  }
}
