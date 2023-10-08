package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public class AuthorMongo {
  @Id
  private String id;

  private String firstname;

  private String lastname;

  public AuthorMongo(String firstname, String lastname) {
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public String getId() {
    return this.id;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String firstname) {
    this.lastname = lastname;
  }
}
