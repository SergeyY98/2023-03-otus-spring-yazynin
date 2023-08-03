package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class CommentRepositoryTest {

  private static final String EXISTING_BOOK_NAME = "book_01";

  private static final List<Author> EXISTING_AUTHORS = List.of(new Author("firstname_01","lastname_01"),
      new Author("firstname_02","lastname_02"));

  private static final List<Genre> EXISTING_GENRES = List.of(new Genre("genre_01"),
      new Genre("genre_02"));

  private static final Book EXISTING_BOOK = new Book(EXISTING_BOOK_NAME,
      EXISTING_AUTHORS, EXISTING_GENRES);

  private static final int COMMENT_AMMOUNT = 2;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @DisplayName("добавлять комментарий в БД")
  @Test
  void shouldInsertComment() {
    long cnt = commentRepository.count();
    val existingBook = mongoTemplate.findAll(Book.class).get(0);
    Comment expectedComment = new Comment("New commentator", "New text", existingBook);
    commentRepository.save(expectedComment);
    Comment actualComment = mongoTemplate.findById(expectedComment.getId(), Comment.class);
    assertThat(cnt + 1).usingRecursiveComparison().isEqualTo(commentRepository.count());
  }

  @DisplayName("обновлять комментарий в БД")
  @Test
  void shouldUpdateComment() {
    val existingId = mongoTemplate.findAll(Comment.class).get(0).getId();
    Comment expectedComment = new Comment(existingId, "New commentator", "New text", null);
    Comment savedComment = commentRepository.save(expectedComment);
    Comment actualComment = mongoTemplate.findById(savedComment.getId(), Comment.class);
    assertThat(actualComment).usingRecursiveComparison().isEqualTo(savedComment);
  }

  @DisplayName("удалять заданный комментарий по его id")
  @Test
  void shouldCorrectDeleteCommentById() {
    val existingComment = mongoTemplate.findAll(Comment.class).get(0);
    String id = existingComment.getId();
    assertThat(existingComment).isNotNull();

    commentRepository.deleteById(id);
    val deletedComment = mongoTemplate.findById(id, Comment.class);

    assertThat(deletedComment).isNull();
  }

  @DisplayName("возвращать ожидаемый список комментариев")
  @Test
  void shouldContainExpectedCommentInList() {
    List<Comment> actualCommentList = commentRepository
        .findAllByBookId(mongoTemplate.findAll(Book.class).get(0).getId());
    assertThat(actualCommentList.size()).isEqualTo(COMMENT_AMMOUNT);
  }
}
