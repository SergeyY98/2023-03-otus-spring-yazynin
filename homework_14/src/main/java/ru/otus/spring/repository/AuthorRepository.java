package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.AuthorMongo;

public interface AuthorRepository extends MongoRepository<AuthorMongo, String> {
}
