package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.dao.ext.BookAuthorRelation;
import ru.otus.spring.dao.ext.BookGenreRelation;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Objects;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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
    return count == null? 0: count;
  }

  @Override
  public void insert(Book book) {
    namedParameterJdbcOperations.update("insert into books (id, name) values (:id, :name)",
        Map.of("id", book.getId(), "name", book.getName()));
  }

  @Override
  public Book findById(long id) {
    List<Author> authors = authorDao.findAll();
    List<Genre> genres = genreDao.findAll();
    List<BookAuthorRelation> bookAuthorRelations = getBookAuthorRelations();
    List<BookGenreRelation> bookGenreRelations = getBookGenreRelations();
    Map<String, Object> params = Collections.singletonMap("id", id);
    Map<Long, Book> books =
        namedParameterJdbcOperations.query(
            "select id, name from books where id = :id", params,
            new BookMapper()).stream().collect(Collectors.toMap(Book::getId, Function.identity()));
    mergeStudentsInfo(books, authors, genres, bookAuthorRelations, bookGenreRelations);
    return Objects.requireNonNull(books).values().stream().findFirst().orElseThrow();
  }

  @Override
  public List<Book> findAll() {
    List<Author> authors = authorDao.findAll();
    List<Genre> genres = genreDao.findAll();
    List<BookAuthorRelation> bookAuthorRelations = getBookAuthorRelations();
    List<BookGenreRelation> bookGenreRelations = getBookGenreRelations();
    List<BookAuthorRelation> relations = getBookAuthorRelations();
    Map<Long, Book> books =
        namedParameterJdbcOperations.query("select id, name from books",
            new BookMapper()).stream().collect(Collectors.toMap(Book::getId, Function.identity()));
    mergeStudentsInfo(books, authors, genres, bookAuthorRelations, bookGenreRelations);
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

  private void mergeStudentsInfo(Map<Long, Book> books, List<Author> authors, List<Genre> genres,
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
      return new Book(id, name, genres, authors);
    }
  }
}
