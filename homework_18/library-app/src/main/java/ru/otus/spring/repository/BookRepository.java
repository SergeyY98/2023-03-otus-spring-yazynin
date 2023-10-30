package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
