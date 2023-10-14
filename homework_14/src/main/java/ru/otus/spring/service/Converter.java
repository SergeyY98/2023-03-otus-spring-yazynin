package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.BookMongo;
import ru.otus.spring.domain.AuthorMongo;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.CommentMongo;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.GenreMongo;
import java.util.stream.Collectors;

@Service
public class Converter {

  public AuthorMongo convertAuthor(Author a) {
    return new AuthorMongo(String.valueOf(a.getId()), a.getFirstname(), a.getLastname());
  }

  public GenreMongo convertGenre(Genre g) {
    return new GenreMongo(String.valueOf(g.getId()), g.getName());
  }

  public BookMongo convertBook(Book b) {
    return new BookMongo(String.valueOf(b.getId()), b.getName(),
        b.getAuthors().stream().map(this::convertAuthor).collect(Collectors.toList()),
        b.getGenres().stream().map(this::convertGenre).collect(Collectors.toList()));
  }

  public CommentMongo convertComment(Comment c) {
    BookMongo book = convertBook(c.getBook());
    return new CommentMongo(c.getCommentator(), c.getText(), book);
  }
}
