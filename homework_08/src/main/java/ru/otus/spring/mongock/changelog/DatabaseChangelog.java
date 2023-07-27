package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.repository.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

  private Author author1;

  private Author author2;

  private Genre genre1;

  private Genre genre2;

  private Book book;

  @ChangeSet(order = "001", id = "dropDb", author = "stvort", runAlways = true)
  public void dropDb(MongoDatabase db) {
    db.drop();
  }

  @ChangeSet(order = "002", id = "insertAuthor", author = "SergeyY98")
  public void insertAuthor(AuthorRepository repository) {
    author1 = repository.save(new Author("firstname_01", "lastname_01"));
    author2 = repository.save(new Author("firstname_02", "lastname_02"));
  }

  @ChangeSet(order = "002", id = "insertGenre", author = "SergeyY98")
  public void insertGenre(GenreRepository repository) {
    genre1 = repository.save(new Genre("genre_01"));
    genre2 = repository.save(new Genre("genre_02"));
  }

  @ChangeSet(order = "003", id = "insertBook", author = "SergeyY98")
  public void insertBook(BookRepository repository) {
    book = repository.save(new Book("book_01", List.of(author1, author2), List.of(genre1, genre2)));
  }

  @ChangeSet(order = "004", id = "insertComment", author = "SergeyY98")
  public void insertComment(CommentRepository repository) {
    repository.save(new Comment("author_01", "text_01", book));
    repository.save(new Comment("author_02", "text_02", book));
  }
}