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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao для работы с книгами должно")
@DataJpaTest
@Import({AuthorRepositoryJpa.class, GenreRepositoryJpa.class, BookRepositoryJpa.class, CommentRepositoryJpa.class})
public class BookRepositoryJpaTest {
  private static final int EXPECTED_BOOK_COUNT = 10;

  private static final long EXISTING_BOOK_ID = 1;

  private static final List<Comment> EXISTING_COMMENTS = List.of(new Comment(1, "author_01", "text_01"),
      new Comment(2, "author_02", "text_02"));

  private static final List<Genre> EXISTING_GENRES = List.of(new Genre(1, "genre_01"),
      new Genre(2, "genre_02"), new Genre(3, "genre_03"));

  private static final List<Author> EXISTING_AUTHORS = List.of(new Author(1, "firstname_01","lastname_01"),
      new Author(2, "firstname_02","lastname_02"), new Author(3, "firstname_03","lastname_03"));

  private static final String EXISTING_BOOK_NAME = "book_01";

  @Autowired
  private AuthorRepository authorDao;

  @Autowired
  private GenreRepository genreRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private TestEntityManager em;

  @DisplayName("возвращать ожидаемое количество книг в БД")
  @Test
  void shouldReturnExpectedBookCount() {
    long actualBooksCount = bookRepository.count();
    assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOK_COUNT);
  }

  @DisplayName("возвращать ожидаемую книгу по ее id")
  @Test
  //@Transactional
  void shouldReturnExpectedBookById() {
    Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_COMMENTS,
        EXISTING_AUTHORS, EXISTING_GENRES);
    Book actualBook = bookRepository.findById(EXISTING_BOOK_ID).get();
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @DisplayName("добавлять книгу в БД")
  @Test
  void shouldInsertBook() {
    Book expectedBook = new Book(0,"Roadside picnic", null,
        Stream.of(2, 3).map(i -> authorDao.findById(i).get()).collect(Collectors.toList()),
        Stream.of(1, 3).map(i -> genreRepository.findById(i).get()).collect(Collectors.toList()));
    bookRepository.update(expectedBook);
    commentRepository.update(new Comment(0, "Zack", "Awesome charachters"));
    commentRepository.update(new Comment(0, "Mike", "I love this book"));
    Book actualBook = bookRepository.findById(expectedBook.getId()).get();
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @DisplayName("обновлять книгу в БД")
  @Test
  void shouldUpdateBook() {
    Book expectedBook = new Book(2,"Roadside picnic",
        bookRepository.findById(2).get().getComments(),
        Stream.of(2, 3).map(i -> authorDao.findById(i).get()).collect(Collectors.toList()),
        Stream.of(1, 3).map(i -> genreRepository.findById(i).get()).collect(Collectors.toList()));
    bookRepository.update(expectedBook);
    Book actualBook = bookRepository.findById(expectedBook.getId()).get();
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @DisplayName("удалять заданную книгу по ее id")
  @Test
  void shouldCorrectDeleteBookById() {
    assertThatCode(() -> bookRepository.findById(EXISTING_BOOK_ID).get())
        .doesNotThrowAnyException();

    bookRepository.deleteById(EXISTING_BOOK_ID);

    assertThatThrownBy(() -> bookRepository.findById(EXISTING_BOOK_ID).get())
        .isInstanceOf(NoSuchElementException.class);
  }

  @DisplayName("возвращать ожидаемый список книг")
  @Test
  void shouldContainExpectedBookInList() {
    Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_COMMENTS,
        EXISTING_AUTHORS, EXISTING_GENRES);
    List<Book> actualBookList = bookRepository.findAll();
    assertThat(actualBookList.get(0))
        .usingRecursiveComparison().isEqualTo(expectedBook);
  }
}