package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.ext.BookAuthorRelation;
import ru.otus.spring.dao.ext.BookGenreRelation;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  private final AuthorDao authorDao;

  private final GenreDao genreDao;

  @Override
  public int count() {
    Integer count = namedParameterJdbcOperations.queryForObject("select count(*) from books", Map.of(), Integer.class);
    return count == null ? 0 : count;
  }

  @Override
  public void insert(Book book) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("name", book.getName());
    namedParameterJdbcOperations.update("insert into books (name) values (:name)", namedParameters, keyHolder);
    insertBookRelations(book);
  }

  @Override
  public void update(Book book) {
    namedParameterJdbcOperations.update("update books set name=:name where id=:id",
        Map.of("id", book.getId(), "name", book.getName()));
    namedParameterJdbcOperations.update("delete from books_authors where book_id = :id", Map.of("id", book.getId()));
    namedParameterJdbcOperations.update("delete from books_genres where book_id = :id", Map.of("id", book.getId()));
    insertBookRelations(book);
  }

  @Override
  public Book findById(long id) {
    List<Author> authors = authorDao.findAllWithRelations();
    List<Genre> genres = genreDao.findAllWithRelations();
    List<BookAuthorRelation> bookAuthorRelations = getBookAuthorRelations();
    List<BookGenreRelation> bookGenreRelations = getBookGenreRelations();
    Map<String, Object> params = Collections.singletonMap("id", id);
    Map<Long, Book> books =
        namedParameterJdbcOperations.query(
            "select id, name from books where id = :id", params,
            new BookMapper()).stream().collect(Collectors.toMap(Book::getId, Function.identity()));
    mergeBooksInfo(books, authors, genres, bookAuthorRelations, bookGenreRelations);
    return Objects.requireNonNull(books).values().stream().findFirst().orElseThrow();
  }

  @Override
  public void insertBookRelations(Book book) {
    List<Author> authors = book.getAuthors();
    List<Genre> genres = book.getGenres();
    List<Map<String, Object>> batchAuthorValues = new ArrayList<>(authors.size());
    for (Author author : authors) {
      batchAuthorValues.add(
          new MapSqlParameterSource("book_id", book.getId())
              .addValue("author_id", author.getId()).getValues());
    }
    List<Map<String, Object>> batchGenreValues = new ArrayList<>(genres.size());
    for (Genre genre : genres) {
      batchGenreValues.add(
          new MapSqlParameterSource("book_id", book.getId())
              .addValue("genre_id", genre.getId()).getValues());
    }
    namedParameterJdbcOperations.batchUpdate(
        "insert into books_authors (book_id, author_id) values (:book_id, :author_id)",
        batchAuthorValues.toArray(new Map[authors.size()]));
    namedParameterJdbcOperations.batchUpdate(
        "insert into books_genres (book_id, genre_id) values (:book_id, :genre_id)",
        batchGenreValues.toArray(new Map[authors.size()]));
  }

  @Override
  public List<Book> findAll() {
    List<Author> authors = authorDao.findAllWithRelations();
    List<Genre> genres = genreDao.findAllWithRelations();
    List<BookAuthorRelation> bookAuthorRelations = getBookAuthorRelations();
    List<BookGenreRelation> bookGenreRelations = getBookGenreRelations();
    Map<Long, Book> books =
        namedParameterJdbcOperations.query("select id, name from books",
            new BookMapper()).stream().collect(Collectors.toMap(Book::getId, Function.identity()));
    mergeBooksInfo(books, authors, genres, bookAuthorRelations, bookGenreRelations);
    return new ArrayList<>(Objects.requireNonNull(books).values());
  }

  private List<BookAuthorRelation> getBookAuthorRelations() {
    return namedParameterJdbcOperations.query(
        "select book_id, author_id from books_authors ab order by book_id, author_id",
        (rs, i) -> new BookAuthorRelation(rs.getLong(1), rs.getLong(2)));
  }

  private List<BookGenreRelation> getBookGenreRelations() {
    return namedParameterJdbcOperations.query(
        "select book_id, genre_id from books_genres ab order by book_id, genre_id",
        (rs, i) -> new BookGenreRelation(rs.getLong(1), rs.getLong(2)));
  }

  private void mergeBooksInfo(Map<Long, Book> books, List<Author> authors, List<Genre> genres,
                                 List<BookAuthorRelation> bookAuthorRelations,
                                 List<BookGenreRelation> bookGenreRelations) {
    Map<Long, Author> authorsMap = authors.stream().collect(Collectors.toMap(Author::getId, Function.identity()));
    Map<Long, Genre> genresMap = genres.stream().collect(Collectors.toMap(Genre::getId, Function.identity()));
    bookAuthorRelations.forEach(r -> {
      if (books.containsKey(r.getBookId()) && authorsMap.containsKey(r.getAuthorId())) {
        books.get(r.getBookId()).getAuthors().add(authorsMap.get(r.getAuthorId()));
      }
    });
    bookGenreRelations.forEach(r -> {
      if (books.containsKey(r.getBookId()) && genresMap.containsKey(r.getGenreId())) {
        books.get(r.getBookId()).getGenres().add(genresMap.get(r.getGenreId()));
      }
    });
  }

  @Override
  public void deleteById(long id) {
    Map<String, Object> params = Collections.singletonMap("id", id);
    namedParameterJdbcOperations.update(
        "delete from books where id = :id", params
    );
  }

  private static class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String name = resultSet.getString("name");
      List<Author> authors = new ArrayList<>();
      List<Genre> genres = new ArrayList<>();
      return new Book(id, name, authors, genres);
    }
  }
}
