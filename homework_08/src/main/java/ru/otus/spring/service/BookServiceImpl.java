package ru.otus.spring.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
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

  private final CommentRepository commentRepository;

  @Autowired
  public BookServiceImpl(IOService ioService, AuthorRepository authorDao,
                         GenreRepository genreRepository, BookRepository bookRepository,
                         CommentRepository commentRepository) {
    this.ioService = ioService;
    this.authorDao = authorDao;
    this.genreRepository = genreRepository;
    this.bookRepository = bookRepository;
    this.commentRepository = commentRepository;
  }

  @Override
  public String findAll() {
    return bookRepository.findAll().stream()
        .map(b -> b.getId() + " " + b.getName() + "\n" + "Authors:\n" +
            b.getAuthors().stream().map(a -> a.getFirstname() + " " + a.getLastname())
                .collect(Collectors.joining("\n")) +
            "\nGenres:\n" + b.getGenres().stream().map(Genre::getName)
            .collect(Collectors.joining("\n"))).collect(Collectors.joining("\n"));
  }

  @Override
  public String findById(String id) {
    try {
      Book b = bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
      return b.getName() + "\n" + "Authors:\n" +
          b.getAuthors().stream().map(a -> a.getFirstname() + " " + a.getLastname())
              .collect(Collectors.joining("\n")) +
          "\nGenres:\n" + b.getGenres().stream().map(Genre::getName).collect(Collectors.joining("\n"));
    } catch (NoSuchElementException e) {
      return "No books with selected id found";
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(String id) {
    bookRepository.deleteById(id);
    commentRepository.deleteAllByBookId(id);
  }

  @Transactional(readOnly = false)
  @Override
  public void save(String name, List<String> authors, List<String> genres) {
    bookRepository.save(
        new Book(name,
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
