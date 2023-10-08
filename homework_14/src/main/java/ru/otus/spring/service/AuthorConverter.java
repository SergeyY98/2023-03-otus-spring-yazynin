package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.AuthorMongo;

@Service
public class AuthorConverter {
  public AuthorMongo convert(Author author) {
    return new AuthorMongo(author.getFirstname(), author.getLastname());
  }
}
