package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

public interface BookJpaRepository extends JpaRepository<Book, Long> {
}
