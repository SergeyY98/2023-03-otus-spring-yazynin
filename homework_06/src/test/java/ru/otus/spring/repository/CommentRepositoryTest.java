package ru.otus.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({CommentRepositoryJpa.class})
public class CommentRepositoryTest {
  private static final long EXISTING_COMMENT_ID = 1;

  private static final long EXISTING_BOOK_ID = 1;

  private static final String EXISTING_BOOK_NAME = "book_01";

  private static final List<Author> EXISTING_AUTHORS = List.of(new Author(1, "firstname_01","lastname_01"),
      new Author(2, "firstname_02","lastname_02"), new Author(3, "firstname_03","lastname_03"));

  private static final List<Genre> EXISTING_GENRES = List.of(new Genre(1, "genre_01"),
      new Genre(2, "genre_02"), new Genre(3, "genre_03"));

  private static final Book EXISTING_BOOK = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME,
      EXISTING_AUTHORS, EXISTING_GENRES);

  private static final int COMMENT_AMMOUNT = 2;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private TestEntityManager em;

  @DisplayName("добавлять комментарий в БД")
  @Test
  void shouldInsertComment() {
    Comment expectedComment = new Comment(0, "New commentator", "New text", EXISTING_BOOK);
    commentRepository.update(expectedComment);
    Comment actualComment = em.find(Comment.class, expectedComment.getId());
    assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
  }

  @DisplayName("обновлять комментарий в БД")
  @Test
  void shouldUpdateComment() {
    Comment expectedComment = new Comment(1, "New commentator", "New text", EXISTING_BOOK);
    commentRepository.update(expectedComment);
    Comment actualComment = em.find(Comment.class, expectedComment.getId());
    assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
  }

  @DisplayName("удалять заданный комментарий по его id")
  @Test
  void shouldCorrectDeleteCommentById() {
    val existingComment = em.find(Comment.class, EXISTING_COMMENT_ID);
    assertThat(existingComment).isNotNull();

    commentRepository.deleteById(EXISTING_COMMENT_ID);
    val deletedGenre = em.find(Comment.class,  EXISTING_COMMENT_ID);

    assertThat(deletedGenre).isNull();
  }

  @DisplayName("возвращать ожидаемый список комментариев")
  @Test
  void shouldContainExpectedCommentInList() {
    List<Comment> actualCommentList = commentRepository.findAllByBookId(EXISTING_BOOK_ID);
    assertThat(actualCommentList.size()).isEqualTo(COMMENT_AMMOUNT);
  }
}
