package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Book;

import java.util.List;

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
        authors.stream().map(AuthorDto::toDomainObject).toList(),
        genres.stream().map(GenreDto::toDomainObject).toList());
  }

  public static BookDto fromDomainObject(Book book) {
    return new BookDto(book.getId(), book.getName(),
        book.getAuthors().stream().map(AuthorDto::fromDomainObject).toList(),
        book.getGenres().stream().map(GenreDto::fromDomainObject).toList());
  }
}
