package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Genre;

public interface GenreJpaRepository extends JpaRepository<Genre, Long> {
}
