package ru.otus.spring.domain;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "commentator", nullable = false)
  private String commentator;

  @Column(name = "text", nullable = false)
  private String text;

  @ManyToOne(fetch = FetchType.LAZY)
  private Book book;

  public long getId() {
    return this.id;
  }

  public String getCommentator() {
    return this.commentator;
  }

  public String getText() {
    return this.text;
  }

  public Book getBook() {
    return this.book;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setCommentator(String commentator) {
    this.commentator = commentator;
  }

  public void setAuthors(String text) {
    this.text = text;
  }

  public void setBook(Book book) {
    this.book = book;
  }
}
