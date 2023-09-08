package ru.otus.spring.repository.ext;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BookGenreRelation {
  private final long bookId;

  private final long genreId;
}

