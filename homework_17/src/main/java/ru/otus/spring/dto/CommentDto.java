package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

  private long id;

  private String commentator;

  private String text;

  private Book book;

  public Comment toDomainObject() {
    return new Comment(id, commentator, text, book);
  }

  public static CommentDto fromDomainObject(Comment comment) {
    return new CommentDto(comment.getId(), comment.getCommentator(), comment.getText(), comment.getBook());
  }
}
