package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно")
@JdbcTest
@Import({AuthorDaoJdbc.class, GenreDaoJdbc.class, BookDaoJdbc.class})
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BookDaoJdbcTest {
  private static final int EXPECTED_BOOK_COUNT = 2;
  private static final int EXISTING_BOOK_ID = 2;
  private static final List<Genre> EXISTING_GENRES = List.of(new Genre(1, "Science fiction"), new Genre(3, "Philosophy"));
  private static final List<Author> EXISTING_AUTHORS = List.of(new Author(2, "Boris","Strugatsky"), new Author(3, "Arcadiy","Strugatsky"));
  private static final String EXISTING_BOOK_NAME = "Escape Attempt";

  @Autowired
  private BookDaoJdbc bookDao;

  @BeforeTransaction
  void beforeTransaction(){
    System.out.println("beforeTransaction");
  }

  @AfterTransaction
  void afterTransaction(){
    System.out.println("afterTransaction");
  }

  @DisplayName("возвращать ожидаемое количество книг в БД")
  @Test
  void shouldReturnExpectedBookCount() {
    int actualBooksCount = bookDao.count();
    assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOK_COUNT);
  }

  //@Rollback(value = false)
  //@Commit
  @DisplayName("добавлять книгу в БД")
  @Test
  void shouldInsertBook() {
    Book expectedBook = new Book(3,"Roadside picnic", List.of(), List.of());
    bookDao.insert(expectedBook);
    Book actualPerson = bookDao.findById(expectedBook.getId());
    assertThat(actualPerson).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @DisplayName("возвращать ожидаемую книгу по ее id")
  @Test
  void shouldReturnExpectedPersonById() {
    Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_GENRES, EXISTING_AUTHORS);
    Book actualBook = bookDao.findById(expectedBook.getId());
    assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
  }

  @DisplayName("удалять заданную книгу по ее id")
  @Test
  void shouldCorrectDeleteBookById() {
    assertThatCode(() -> bookDao.findById(EXISTING_BOOK_ID))
        .doesNotThrowAnyException();

    bookDao.deleteById(EXISTING_BOOK_ID);

    assertThatThrownBy(() -> bookDao.findById(EXISTING_BOOK_ID))
        .isInstanceOf(NoSuchElementException.class);
  }

  @DisplayName("возвращать ожидаемый список книг")
  @Test
  void shouldContainExpectedBookInList() {
    Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, EXISTING_GENRES, EXISTING_AUTHORS);
    List<Book> actualBookList = bookDao.findAll();
    assertThat(actualBookList)
        .contains(expectedBook);
  }
}
