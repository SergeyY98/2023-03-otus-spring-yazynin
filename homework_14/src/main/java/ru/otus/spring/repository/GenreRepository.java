package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domain.GenreMongo;

public interface GenreRepository extends MongoRepository<GenreMongo, String> {
}

