package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
}
