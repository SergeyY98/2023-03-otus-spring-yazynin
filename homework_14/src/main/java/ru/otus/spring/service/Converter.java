package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookMongo;
import ru.otus.spring.domain.AuthorMongo;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.CommentMongo;
import ru.otus.spring.domain.GenreMongo;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookJpaRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentJpaRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class Converter {

  private final AuthorRepository authorRepository;

  private final GenreRepository genreRepository;

  private final BookRepository bookRepository;

  private final BookJpaRepository bookJpaRepository;

  private final CommentJpaRepository commentJpaRepository;

  @Autowired
  public Converter(AuthorRepository authorRepository, GenreRepository genreRepository,
                   BookRepository bookRepository, BookJpaRepository bookJpaRepository,
                   CommentJpaRepository commentJpaRepository) {
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
    this.bookRepository = bookRepository;
    this.bookJpaRepository = bookJpaRepository;
    this.commentJpaRepository = commentJpaRepository;
  }

  public BookMongo convertBook(Book book) {
    Book b = bookJpaRepository.findById(book.getId()).orElseThrow(NoSuchElementException::new);
    return new BookMongo(b.getName(),
        b.getAuthors().stream().map(author -> authorRepository
            .save(new AuthorMongo(author.getFirstname(), author.getLastname()))).collect(Collectors.toList()),
        b.getGenres().stream().map(genre -> genreRepository
            .save(new GenreMongo(genre.getName()))).collect(Collectors.toList()));
  }

  public CommentMongo convertComment(Comment comment) {
    Comment c = commentJpaRepository.findById(comment.getId()).orElseThrow(NoSuchElementException::new);
    String name = c.getBook().getName();
    BookMongo book = bookRepository.findByName(name).orElseThrow(NoSuchElementException::new);
    return new CommentMongo(c.getCommentator(), c.getText(), book);
  }
}
