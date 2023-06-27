package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  @Override
  public int count() {
    Integer count = namedParameterJdbcOperations.queryForObject(
        "select count(*) from authors", Map.of(), Integer.class);
    return count == null? 0: count;
  }

  @Override
  public void insert(Author author) {
    namedParameterJdbcOperations.update("insert into authors (firstname,lastname) values (:firstname, :lastname)",
        Map.of("firstname", author.getFirstname(), "lastname", author.getLastname()));
  }

  @Override
  public Author findById(long id) {
    Map<String, Object> params = Collections.singletonMap("id", id);
    return namedParameterJdbcOperations.queryForObject(
        "select id, firstname, lastname from authors where id = :id", params, new AuthorMapper()
    );
  }

  @Override
  public List<Author> findAll() {
    return namedParameterJdbcOperations.query("select a.id, a.firstname, a.lastname " +
        "from authors a inner join books_authors ab on a.id = ab.author_id " +
        "group by a.id, a.firstname, a.lastname " +
        "order by a.firstname, a.lastname", new AuthorMapper());
  }

  @Override
  public void deleteById(long id) {
    Map<String, Object> params = Collections.singletonMap("id", id);
    namedParameterJdbcOperations.update(
        "delete from authors where id = :id", params
    );
  }

  private static class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String firstName = resultSet.getString("firstname");
      String lastName = resultSet.getString("lastname");
      return new Author(id, firstName, lastName);
    }
  }
}
