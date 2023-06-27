package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
  private final IOService ioService;

  private final BookDao bookDao;

  @Autowired
  public BookServiceImpl(IOService ioService, BookDao bookDao) {
    this.ioService = ioService;
    this.bookDao = bookDao;
  }

  @Override
  public void findAll() {
    bookDao.findAll().stream()
        .map(b -> b.getId() + ") " + b.getName() + "\n" + "Authors:\n" +
            b.getAuthors().stream().map(a -> a.getId() + ". " +
                a.getFirstname() + " " + a.getLastname()).collect(Collectors.joining("\n")) +
            "\nGenres:\n" + b.getGenres().stream().map(g -> g.getId() + ". " +
            g.getName()).collect(Collectors.joining("\n")))
        .forEach(ioService::outputString);
  }

  @Override
  public void findById(long id) {
    Book b = bookDao.findById(id);
    ioService.outputString(b.getId() + ") " + b.getName() + "\n" + "Authors:\n" +
            b.getAuthors().stream().map(a -> a.getId() + ". " +
                a.getFirstname() + " " + a.getLastname()).collect(Collectors.joining("\n")) +
            "\nGenres:\n" + b.getGenres().stream().map(g -> g.getId() + ". " +
                g.getName()).collect(Collectors.joining("\n")));
  }

  @Override
  public void deleteById(long id) {
    bookDao.deleteById(id);
  }

  @Override
  public void insert(String name) {
    bookDao.insert(new Book(bookDao.count()+1, name, List.of(), List.of()));
  }

  @Override
  public void count() {
    ioService.outputString("Book count: " + bookDao.count());
  }
}
