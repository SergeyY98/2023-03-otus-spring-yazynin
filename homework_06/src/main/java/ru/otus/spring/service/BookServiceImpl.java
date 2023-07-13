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
  public void findAll() {
    try {
      bookRepository.findAll().stream()
          .map(b -> b.getId() + ") " + b.getName() + "\n" + "Authors:\n" +
              b.getAuthors().stream().map(a -> a.getId() + ". " +
                  a.getFirstname() + " " + a.getLastname()).collect(Collectors.joining("\n")) +
              "\nGenres:\n" + b.getGenres().stream().map(g -> g.getId() + ". " + g.getName())
              .collect(Collectors.joining("\n")) +
              "\nComments:\n" + b.getComments().stream().map(c -> c.getId() + ". " +
              c.getCommentator() + " : " + c.getText()).collect(Collectors.joining("\n")))
          .forEach(ioService::outputString);
    } catch (NoSuchElementException e) {
      ioService.outputString("No books found");
    }
  }

  @Override
  public void findById(long id) {
    try {
      Book b = bookRepository.findById(id).get();
      ioService.outputString(b.getId() + ") " + b.getName() + "\n" + "Authors:\n" +
          b.getAuthors().stream().map(a -> a.getId() + ". " +
              a.getFirstname() + " " + a.getLastname()).collect(Collectors.joining("\n")) +
          "\nGenres:\n" + b.getGenres().stream()
          .map(g -> g.getId() + ". " + g.getName()).collect(Collectors.joining("\n")) +
          "\nComments:\n" + b.getComments().stream().map(c -> c.getId() + ". " +
          c.getCommentator() + " : " + c.getText()).collect(Collectors.joining("\n")));
    } catch (NoSuchElementException e) {
      ioService.outputString("No books with selected id found");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
    try {
      bookRepository.findById(id);
      bookRepository.deleteById(id);
    } catch (NoSuchElementException e) {
      ioService.outputString("No book with selected id found");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void update(long id, String name, List<Long> authors, List<Long> genres) {
    bookRepository.update(
        new Book(id, name, null,
            authors.stream().map(a -> authorDao.findById(a).get()).collect(Collectors.toList()),
            genres.stream().map(g -> genreRepository.findById(g).get()).collect(Collectors.toList())));
  }

  @Override
  public void count() {
    ioService.outputString("Book count: " + bookRepository.count());
  }
}
