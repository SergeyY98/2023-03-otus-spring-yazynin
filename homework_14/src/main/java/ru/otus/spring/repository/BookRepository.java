package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.BookMongo;

public interface BookRepository extends MongoRepository<BookMongo, String>, BookRepositoryCustom {
}
