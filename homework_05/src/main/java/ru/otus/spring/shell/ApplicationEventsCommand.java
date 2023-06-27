package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.BookServiceImpl;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommand {

  private final BookServiceImpl bookServiceImpl;

  @ShellMethod(value = "Find all books", key = {"fB", "findAllBooks"})
  public void getAllBooks() {
    bookServiceImpl.findAll();
  }

  @ShellMethod(value = "Find book by id", key = {"fb", "findBookById"})
  public void getBookById(@ShellOption(defaultValue = "1") String id) {
    bookServiceImpl.findById(Long.parseLong(id));
  }

  @ShellMethod(value = "Delete book by id", key = {"db", "deleteBookById"})
  public void deleteBookById(@ShellOption(defaultValue = "1") String id) {
    bookServiceImpl.deleteById(Long.parseLong(id));
  }

  @ShellMethod(value = "Insert book", key = {"ib", "insertBook"})
  public void insertBook(@ShellOption(defaultValue = "Book") String name) {
    bookServiceImpl.insert(name);
  }

  @ShellMethod(value = "Delete book by id", key = {"c", "countBook"})
  public void countBook() {
    bookServiceImpl.count();
  }
}

