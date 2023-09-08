package ru.otus.spring.domain;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "firstname", nullable = false)
  private String firstname;

  @Column(name = "lastname", nullable = false)
  private String lastname;

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

}
