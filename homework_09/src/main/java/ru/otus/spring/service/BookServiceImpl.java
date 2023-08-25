package ru.otus.spring.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
public class BookServiceImpl implements BookService {
  private final IOService ioService;

  private final AuthorRepository authorDao;

  private final GenreRepository genreRepository;

  private final BookRepository bookRepository;

  @Autowired
  public BookServiceImpl(IOService ioService, AuthorRepository authorDao,
                         GenreRepository genreRepository, BookRepository bookRepository) {
    this.ioService = ioService;
    this.authorDao = authorDao;
    this.genreRepository = genreRepository;
    this.bookRepository = bookRepository;
  }

  @Override
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @Override
  public Book findById(long id) {
    try {
      return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    } catch (NoSuchElementException e) {
      return new Book();
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
    try {
      bookRepository.deleteById(id);
    } catch (NoSuchElementException e) {
      ioService.outputString("No book with selected id found");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void save(Book book) {
    bookRepository.save(book);
  }

  @Override
  public void count() {
    ioService.outputString("Book count: " + bookRepository.count());
  }
}
