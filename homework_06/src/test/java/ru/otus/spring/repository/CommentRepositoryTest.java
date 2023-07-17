package ru.otus.spring.repository;

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
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

  private static final List<Comment> EXISTING_BOOK_COMMENTS = List.of(
      new Comment(1, "author_01", "text_01", EXISTING_BOOK),
      new Comment(2, "author_02", "text_02", EXISTING_BOOK)
  );

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private TestEntityManager em;

  @DisplayName("добавлять комментарий в БД")
  @Test
  void shouldInsertComment() {
    Comment expectedComment = new Comment(0, "New commentator", "New text", EXISTING_BOOK);
    commentRepository.update(expectedComment);
    Comment actualGenre = commentRepository.findById(expectedComment.getId()).orElseThrow(NoSuchElementException::new);
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedComment);
  }

  @DisplayName("обновлять комментарий в БД")
  @Test
  void shouldUpdateComment() {
    Comment expectedComment = new Comment(1, "New commentator", "New text", EXISTING_BOOK);
    commentRepository.update(expectedComment);
    Comment actualGenre = commentRepository.findById(expectedComment.getId()).orElseThrow(NoSuchElementException::new);
    assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedComment);
  }

  @DisplayName("удалять заданный комментарий по его id")
  @Test
  void shouldCorrectDeleteCommentById() {
    assertThatCode(() -> commentRepository.findById(EXISTING_COMMENT_ID).orElseThrow(NoSuchElementException::new))
        .doesNotThrowAnyException();

    commentRepository.deleteById(EXISTING_COMMENT_ID);

    assertThatThrownBy(() -> commentRepository.findById(EXISTING_COMMENT_ID).orElseThrow(NoSuchElementException::new))
        .isInstanceOf(NoSuchElementException.class);
  }

  @DisplayName("возвращать ожидаемый список комментариев")
  @Test
  void shouldContainExpectedCommentInList() {
    List<Comment> actualBookList = commentRepository.findAllByBookId(EXISTING_BOOK_ID);
    assertThat(actualBookList).usingRecursiveComparison().isEqualTo(EXISTING_BOOK_COMMENTS);
  }
}
