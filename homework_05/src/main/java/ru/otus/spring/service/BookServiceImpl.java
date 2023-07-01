package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
  private final IOService ioService;

  private final AuthorDao authorDao;

  private final GenreDao genreDao;

  private final BookDao bookDao;

  @Autowired
  public BookServiceImpl(IOService ioService, AuthorDao authorDao,
                         GenreDao genreDao, BookDao bookDao) {
    this.ioService = ioService;
    this.authorDao = authorDao;
    this.genreDao = genreDao;
    this.bookDao = bookDao;
  }

  @Override
  public void findAll() {
    try {
      bookDao.findAll().stream()
          .map(b -> b.getId() + ") " + b.getName() + "\n" + "Authors:\n" +
              b.getAuthors().stream().map(a -> a.getId() + ". " +
                  a.getFirstname() + " " + a.getLastname()).collect(Collectors.joining("\n")) +
              "\nGenres:\n" + b.getGenres().stream().map(g -> g.getId() + ". " +
              g.getName()).collect(Collectors.joining("\n")))
          .forEach(ioService::outputString);
    } catch (NoSuchElementException e) {
      ioService.outputString("No books found");
    }
  }

  @Override
  public void findById(long id) {
    try {
      Book b = bookDao.findById(id);
      ioService.outputString(b.getId() + ") " + b.getName() + "\n" + "Authors:\n" +
              b.getAuthors().stream().map(a -> a.getId() + ". " +
                  a.getFirstname() + " " + a.getLastname()).collect(Collectors.joining("\n")) +
              "\nGenres:\n" + b.getGenres().stream().map(g -> g.getId() + ". " +
                  g.getName()).collect(Collectors.joining("\n")));
    } catch (NoSuchElementException e) {
      ioService.outputString("No books with selected id found");
    }
  }

  @Override
  public void deleteById(long id) {
    try {
      bookDao.findById(id);
      bookDao.deleteById(id);
    } catch (NoSuchElementException e) {
      ioService.outputString("No book with selected id found");
    }
  }

  @Override
  public void insert(String name, List<Long> authors, List<Long> genres) {
    bookDao.insert(
        new Book(name,
            authors.stream().map(authorDao::findById).collect(Collectors.toList()),
            genres.stream().map(genreDao::findById).collect(Collectors.toList())));
  }

  @Override
  public void update(long id, String name, List<Long> authors, List<Long> genres) {
    try {
      bookDao.findById(id);
      bookDao.update(new Book(id, name,
          authors.stream().map(authorDao::findById).collect(Collectors.toList()),
          genres.stream().map(genreDao::findById).collect(Collectors.toList())));
    } catch (NoSuchElementException e) {
      ioService.outputString("No book with selected id found");
    }
  }

  @Override
  public void count() {
    ioService.outputString("Book count: " + bookDao.count());
  }
}
