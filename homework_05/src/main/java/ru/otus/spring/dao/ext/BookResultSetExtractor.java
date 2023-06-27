package ru.otus.spring.dao.ext;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookResultSetExtractor implements
    ResultSetExtractor<Map<Long, Book>> {
  @Override
  public Map<Long, Book> extractData(ResultSet rs) throws SQLException,
      DataAccessException {

    Map<Long, Book> books = new HashMap<>();
    while (rs.next()) {
      long id = rs.getLong("id");
      Book book = books.get(id);
      if (book == null) {
        book = new Book(id, rs.getString("name"),
            new ArrayList<>(), new ArrayList<>());
        books.put(book.getId(), book);
      }

      book.getGenres().add(new Genre(rs.getLong("genre_id"),
          rs.getString("genre_name")));
    }
    return books;
  }
}