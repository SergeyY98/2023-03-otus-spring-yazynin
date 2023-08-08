package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.AuthorService;
import ru.otus.spring.service.GenreService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;
import ru.otus.spring.service.IOService;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommand {

  private final AuthorService authorService;

  private final GenreService genreService;

  private final BookService bookService;

  private final CommentService commentService;

  private final IOService ioService;

  @ShellMethod(value = "Count books", key = {"cb", "countBook"})
  public void countBook() {
    bookService.count();
  }

  @ShellMethod(value = "Find all books", key = {"fB", "findAllBooks"})
  public void getAllBooks() {
    ioService.outputString(bookService.findAll());
  }

  @ShellMethod(value = "Find book by id", key = {"fb", "findBookById"})
  public void getBookById(@ShellOption String id) {
    ioService.outputString(bookService.findById(id));
  }

  @ShellMethod(value = "Delete book by id", key = {"db", "deleteBookById"})
  public void deleteBookById(@ShellOption String id) {
    bookService.deleteById(id);
  }

  @ShellMethod(value = "Update book", key = {"ub", "updateBook"})
  public void updateBook(@ShellOption String name,
                         @ShellOption String authors, @ShellOption String genres) {
    bookService.save(name,
        Arrays.stream(authors.split(",")).collect(Collectors.toList()),
        Arrays.stream(genres.split(",")).collect(Collectors.toList()));
  }

  @ShellMethod(value = "Count authors", key = {"ca", "countAuthor"})
  public void countAuthor() {
    authorService.count();
  }

  @ShellMethod(value = "Find all authors", key = {"fA", "findAllAuthors"})
  public void getAllAuthors() {
    authorService.findAll().stream()
        .map(a -> a.getId() + " " + a.getFirstname() + " " + a.getLastname() + "\n")
        .forEach(ioService::outputString);
  }

  @ShellMethod(value = "Find author by id", key = {"fa", "findAuthorById"})
  public void getAuthorById(@ShellOption String id) {
    try {
      Author a = authorService.findById(id);
      ioService.outputString(a.getFirstname() + " " + a.getLastname());
    } catch (NoSuchElementException e) {
      ioService.outputString("No author with selected id found");
    }
  }

  @ShellMethod(value = "Delete author by id", key = {"da", "deleteAuthorById"})
  public void deleteAuthorById(@ShellOption String id) {
    authorService.deleteById(id);
  }

  @ShellMethod(value = "Update author", key = {"ua", "updateAuthor"})
  public void updateAuthor(@ShellOption String firstame, @ShellOption String lastname) {
    authorService.save(firstame, lastname);
  }

  @ShellMethod(value = "Count genres", key = {"cg", "countGenre"})
  public void countGenre() {
    genreService.count();
  }

  @ShellMethod(value = "Find all genres", key = {"fG", "findAllGenres"})
  public void getAllGenres() {
    genreService.findAll().stream()
        .map(Genre::getName)
        .forEach(ioService::outputString);
  }

  @ShellMethod(value = "Find genre by id", key = {"fg", "findGenreById"})
  public void getGenreById(@ShellOption String id) {
    try {
      Genre g = genreService.findById(id);
      ioService.outputString(g.getName());
    } catch (NoSuchElementException e) {
      ioService.outputString("No author with selected id found");
    }
  }

  @ShellMethod(value = "Delete genre by id", key = {"dg", "deleteGenreById"})
  public void deleteGenreById(@ShellOption String id) {
    genreService.deleteById(id);
  }

  @ShellMethod(value = "Update genre", key = {"ug", "updateGenre"})
  public void updateGenre(@ShellOption String name) {
    genreService.save(name);
  }

  @ShellMethod(value = "Find comments", key = {"fC", "findComments"})
  public void getComments() {
    ioService.outputString(commentService.findAll());
  }

  @ShellMethod(value = "Find comment by id", key = {"fc", "findCommentsById"})
  public void getCommentById(@ShellOption String id) {
    ioService.outputString(commentService.findAllByBookId(id));
  }

  @ShellMethod(value = "Delete comment by id", key = {"dc", "deleteCommentById"})
  public void deleteCommentById(@ShellOption String id) {
    commentService.deleteById(id);
  }
}

