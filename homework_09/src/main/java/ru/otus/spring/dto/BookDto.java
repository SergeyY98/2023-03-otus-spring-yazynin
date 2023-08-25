package ru.otus.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.spring.domain.Book;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class BookDto {

  private long id;

  @NotBlank(message = "Name is mandatory")
  @Size(min = 2, max = 10, message = "Name should be between 2 and 10 characters")
  private String name;

  @NotNull
  @Size(min = 1)
  private List<AuthorDto> authors;

  @NotNull
  @Size(min = 1)
  private List<GenreDto> genres;

  private List<CommentDto> comments = new ArrayList<>();

  public BookDto() {
    authors = new ArrayList<>();
    authors.add(new AuthorDto());
    genres = new ArrayList<>();
    genres.add(new GenreDto());
  }

  public Book toDomainObject() {
    return new Book(id, name,
        authors.stream().map(AuthorDto::toDomainObject).toList(),
        genres.stream().map(GenreDto::toDomainObject).toList(),
        comments.stream().map(CommentDto::toDomainObject).toList());
  }

  public static BookDto fromDomainObject(Book book) {
    return new BookDto(book.getId(), book.getName(),
        book.getAuthors().stream().map(AuthorDto::fromDomainObject).toList(),
        book.getGenres().stream().map(GenreDto::fromDomainObject).toList(),
        book.getComments().stream().map(CommentDto::fromDomainObject).toList());
  }
}
