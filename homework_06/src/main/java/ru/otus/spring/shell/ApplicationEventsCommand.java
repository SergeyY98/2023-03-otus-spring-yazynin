package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
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
    bookService.findAll().stream()
        .map(b -> b.getId() + ") " + b.getName() + "\n" + "Authors:\n" +
            b.getAuthors().stream().map(a -> a.getId() + ". " +
                a.getFirstname() + " " + a.getLastname()).collect(Collectors.joining("\n")) +
            "\nGenres:\n" + b.getGenres().stream().map(g -> g.getId() + ". " + g.getName())
            .collect(Collectors.joining("\n")))
        .forEach(ioService::outputString);
  }

  @ShellMethod(value = "Find book by id", key = {"fb", "findBookById"})
  public void getBookById(@ShellOption String id) {
    try {
      Book b = bookService.findById(Long.parseLong(id));
      ioService.outputString(b.getId() + ") " + b.getName() + "\n" + "Authors:\n" +
          b.getAuthors().stream().map(a -> a.getId() + ". " +
              a.getFirstname() + " " + a.getLastname()).collect(Collectors.joining("\n")) +
          "\nGenres:\n" + b.getGenres().stream()
          .map(g -> g.getId() + ". " + g.getName()).collect(Collectors.joining("\n")));
    } catch (NoSuchElementException e) {
      ioService.outputString("No books with selected id found");
    }
  }

  @ShellMethod(value = "Delete book by id", key = {"db", "deleteBookById"})
  public void deleteBookById(@ShellOption String id) {
    bookService.deleteById(Long.parseLong(id));
  }

  @ShellMethod(value = "Insert book", key = {"ib", "insertBook"})
  public void insertBook(@ShellOption String name, @ShellOption String authors,
                         @ShellOption String genres) {
    bookService.update(0, name,
        Arrays.stream(authors.split(",")).map(Long::parseLong).collect(Collectors.toList()),
        Arrays.stream(genres.split(",")).map(Long::parseLong).collect(Collectors.toList()));
  }

  @ShellMethod(value = "Update book", key = {"ub", "updateBook"})
  public void updateBook(@ShellOption String id, @ShellOption String name,
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
    authorService.findAll().stream()
        .map(a -> a.getId() + ") " + a.getFirstname() + " " + a.getLastname() + "\n")
        .forEach(ioService::outputString);
  }

  @ShellMethod(value = "Find author by id", key = {"fa", "findAuthorById"})
  public void getAuthorById(@ShellOption String id) {
    try {
      Author a = authorService.findById(Long.parseLong(id));
      ioService.outputString(a.getId() + ") " + a.getFirstname() + " " + a.getLastname());
    } catch (NoSuchElementException e) {
      ioService.outputString("No author with selected id found");
    }
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
    genreService.findAll().stream()
        .map(g -> g.getId() + ") " + g.getName() + "\n")
        .forEach(ioService::outputString);
  }

  @ShellMethod(value = "Find genre by id", key = {"fg", "findGenreById"})
  public void getGenreById(@ShellOption String id) {
    try {
      Genre g = genreService.findById(Long.parseLong(id));
      ioService.outputString(g.getId() + ") " + g.getName());
    } catch (NoSuchElementException e) {
      ioService.outputString("No author with selected id found");
    }
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

  @ShellMethod(value = "Find comment by id", key = {"fC", "findCommentsById"})
  public void getCommentById(@ShellOption String id) {
    commentService.findAllByBookId(Long.parseLong(id)).stream()
        .map(c -> c.getId() + ") " + c.getCommentator() + " " + c.getText() + " " + c.getBook().getName() + "\n")
        .forEach(ioService::outputString);
  }

  @ShellMethod(value = "Delete comment by id", key = {"dc", "deleteCommentById"})
  public void deleteCommentById(@ShellOption String id) {
    commentService.deleteById(Long.parseLong(id));
  }
}

