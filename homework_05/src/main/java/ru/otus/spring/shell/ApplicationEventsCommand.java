package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.GenreService;

import java.util.Arrays;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommand {

  private final AuthorService authorService;

  private final GenreService genreService;

  private final BookService bookService;

  @ShellMethod(value = "Count books", key = {"cb", "countBook"})
  public void countBook() {
    bookService.count();
  }

  @ShellMethod(value = "Find all books", key = {"fB", "findAllBooks"})
  public void getAllBooks() {
    bookService.findAll();
  }

  @ShellMethod(value = "Find book by id", key = {"fb", "findBookById"})
  public void getBookById(@ShellOption String id) {
    bookService.findById(Long.parseLong(id));
  }

  @ShellMethod(value = "Delete book by id", key = {"db", "deleteBookById"})
  public void deleteBookById(@ShellOption String id) {
    bookService.deleteById(Long.parseLong(id));
  }

  @ShellMethod(value = "Insert book", key = {"ib", "insertBook"})
  public void insertBook(@ShellOption String name, @ShellOption String authors,
                         @ShellOption String genres) {
    bookService.insert(name,
        Arrays.stream(authors.split(",")).map(Long::parseLong).collect(Collectors.toList()),
        Arrays.stream(genres.split(",")).map(Long::parseLong).collect(Collectors.toList()));
  }

  @ShellMethod(value = "Update book", key = {"ub", "updateBook"})
  public void insertBook(@ShellOption String id, @ShellOption String name,
                         @ShellOption String authors, @ShellOption String genres) {
    bookService.update(Long.parseLong(id), name,
        Arrays.stream(authors.split(",")).map(Long::parseLong).collect(Collectors.toList()),
        Arrays.stream(genres.split(",")).map(Long::parseLong).collect(Collectors.toList()));
  }

  @ShellMethod(value = "Count authors", key = {"ca", "countAuthor"})
  public void countAuthor() {
    authorService.count();
  }

  @ShellMethod(value = "Find all authors", key = {"fA", "findAllAuthors"})
  public void getAllAuthors() {
    authorService.findAll();
  }

  @ShellMethod(value = "Find author by id", key = {"fa", "findAuthorById"})
  public void getAuthorById(@ShellOption String id) {
    authorService.findById(Long.parseLong(id));
  }

  @ShellMethod(value = "Delete author by id", key = {"da", "deleteAuthorById"})
  public void deleteAuthorById(@ShellOption String id) {
    authorService.deleteById(Long.parseLong(id));
  }

  @ShellMethod(value = "Insert author", key = {"ia", "insertAuthor"})
  public void insertAuthor(@ShellOption String firstname, @ShellOption String lastname) {
    authorService.insert(firstname, lastname);
  }

  @ShellMethod(value = "Update author", key = {"ua", "updateAuthor"})
  public void updateAuthor(@ShellOption String id, @ShellOption String firstame,
                         @ShellOption String lastname) {
    authorService.update(Long.parseLong(id), firstame, lastname);
  }

  @ShellMethod(value = "Count genres", key = {"cg", "countGenre"})
  public void countGenre() {
    genreService.count();
  }

  @ShellMethod(value = "Find all genres", key = {"fG", "findAllGenres"})
  public void getAllGenres() {
    genreService.findAll();
  }

  @ShellMethod(value = "Find genre by id", key = {"fg", "findGenreById"})
  public void getGenreById(@ShellOption String id) {
    genreService.findById(Long.parseLong(id));
  }

  @ShellMethod(value = "Delete genre by id", key = {"dg", "deleteGenreById"})
  public void deleteGenreById(@ShellOption String id) {
    genreService.deleteById(Long.parseLong(id));
  }

  @ShellMethod(value = "Insert genre", key = {"ig", "insertGenre"})
  public void insertGenre(@ShellOption String name) {
    genreService.insert(name);
  }

  @ShellMethod(value = "Update genre", key = {"ug", "updateGenre"})
  public void updateGenre(@ShellOption String id, @ShellOption String name) {
    genreService.update(Long.parseLong(id), name);
  }
}

