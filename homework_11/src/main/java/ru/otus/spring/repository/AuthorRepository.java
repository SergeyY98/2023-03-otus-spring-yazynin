package ru.otus.spring.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.spring.domain.Author;

public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {
}
