package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
