package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class CommentMongo {
  @Id
  private String id;

  private String commentator;

  private String text;

  @DBRef(lazy = true)
  private BookMongo book;

  public CommentMongo(String commentator, String text, BookMongo book) {
    this.commentator = commentator;
    this.text = text;
    this.book = book;
  }

  public String getId() {
    return this.id;
  }

  public String getCommentator() {
    return this.commentator;
  }

  public String getText() {
    return this.text;
  }

  public BookMongo getBook() {
    return this.book;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setCommentator(String commentator) {
    this.commentator = commentator;
  }

  public void setAuthors(String text) {
    this.text = text;
  }

  public void setBook(BookMongo book) {
    this.book = book;
  }
}
