package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {

  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  @Override
  public int count() {
    Integer count = namedParameterJdbcOperations.queryForObject("select count(*) from genres", Map.of(), Integer.class);
    return count == null? 0: count;
  }

  @Override
  public void insert(Genre genre) {
    namedParameterJdbcOperations.update("insert into genres (name) values (:name)",
        Map.of("name", genre.getName()));
  }

  @Override
  public void update(Genre genre) {
    namedParameterJdbcOperations.update("update genres set (name) = (:name)",
        Map.of("name", genre.getName()));
  }

  @Override
  public Genre findById(long id) {
    Map<String, Object> params = Collections.singletonMap("id", id);
    return namedParameterJdbcOperations.queryForObject(
        "select id, name from genres where id = :id", params, new GenreDaoJdbc.GenreMapper()
    );
  }

  @Override
  public List<Genre> findAll() {
    return namedParameterJdbcOperations.query("select id, name from genres", new GenreMapper());
  }

  @Override
  public List<Genre> findAllWithRelations() {
    return namedParameterJdbcOperations.query("select g.id, g.name " +
        "from genres g inner join books_genres gb on g.id = gb.genre_id " +
        "group by g.id, g.name " +
        "order by g.name", new GenreDaoJdbc.GenreMapper());
  }

  @Override
  public void deleteById(long id) {
    Map<String, Object> params = Collections.singletonMap("id", id);
    namedParameterJdbcOperations.update(
        "delete from genres where id = :id", params
    );
  }

  private static class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
      long id = resultSet.getLong("id");
      String name = resultSet.getString("name");
      return new Genre(id, name);
    }
  }
}
