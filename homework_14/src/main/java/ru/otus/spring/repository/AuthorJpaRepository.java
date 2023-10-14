package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Author;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
}
