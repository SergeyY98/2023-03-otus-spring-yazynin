package ru.otus.spring.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import({BookRepositoryJpa.class, CommentRepositoryJpa.class})
public class CommentRepositoryTest {
  private static final int EXISTING_COMMENT_ID = 1;

  private static final int EXISTING_BOOK_ID = 1;

  private static final List<Comment> EXISTING_BOOK_COMMENTS = List.of(new Comment(1, "author_01", "text_01"),
      new Comment(2, "author_02", "text_02"));

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private TestEntityManager em;

  @DisplayName("добавлять жанр в БД")
  @Test
  void shouldInsertComment() {
    Comment expectedComment = new Comment(0, "New commentator", "New text");
    commentRepository.update(expectedComment);
    Comment actualGenre = commentRepository.findById(expectedComment.getId()).get();
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedComment);
  }

  @DisplayName("обновлять жанр в БД")
  @Test
  void shouldUpdateComment() {
    Comment expectedComment = new Comment(1, "New commentator", "New text");
    commentRepository.update(expectedComment);
    Comment actualGenre = commentRepository.findById(expectedComment.getId()).get();
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedComment);
  }

  @DisplayName("возвращать ожидаемый жанр по его id")
  @Test
  void shouldReturnExpectedCommentById() {
    Comment actualComment = commentRepository.findById(EXISTING_COMMENT_ID).get();
    assertThat(actualComment).usingRecursiveComparison().isEqualTo(EXISTING_BOOK_COMMENTS.get(0));
  }

  @DisplayName("удалять заданный жанр по его id")
  @Test
  void shouldCorrectDeleteGenreById() {
    assertThatCode(() -> commentRepository.findById(EXISTING_COMMENT_ID).get())
        .doesNotThrowAnyException();

    commentRepository.deleteById(EXISTING_COMMENT_ID);

    assertThatThrownBy(() -> commentRepository.findById(EXISTING_COMMENT_ID).get())
        .isInstanceOf(NoSuchElementException.class);
  }

  @DisplayName("возвращать ожидаемый список книг")
  @Test
  void shouldContainExpectedBookInList() {
    List<Comment> actualBookList = commentRepository.findAllByBookId(EXISTING_BOOK_ID);
    assertThat(actualBookList).isEqualTo(EXISTING_BOOK_COMMENTS);
  }
}
