package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
  private long id;

  private String name;

  public Genre(String name) {
    this.name = name;
  }
}
