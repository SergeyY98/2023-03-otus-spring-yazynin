package ru.otus.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


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

  @OnDelete(action = OnDeleteAction.CASCADE)
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
