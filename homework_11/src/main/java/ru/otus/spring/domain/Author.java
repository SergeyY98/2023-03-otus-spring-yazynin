package ru.otus.spring.domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Table("authors")
public class Author {
  @Id
  private long id;

  private String firstname;

  private String lastname;

  public Author(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public long getId() {
    return this.id;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public static Author fromRow(Map<String, Object> row) {
    if (row.get("a_id") != null) {
      return new Author(
          Long.parseLong(row.get("a_id").toString()),
          row.get("firstname").toString(),
          row.get("lastname").toString());
    } else {
      return null;
    }
  }

}