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
import java.util.stream.Collectors;

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
    return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
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
  public void update(long id, String name, List<Long> authors, List<Long> genres) {
    bookRepository.update(
        new Book(id, name,
            authors.stream().map(a -> authorDao.findById(a).orElseThrow(NoSuchElementException::new))
                .collect(Collectors.toList()),
            genres.stream().map(g -> genreRepository.findById(g).orElseThrow(NoSuchElementException::new))
                .collect(Collectors.toList())));
  }

  @Override
  public void count() {
    ioService.outputString("Book count: " + bookRepository.count());
  }
}
