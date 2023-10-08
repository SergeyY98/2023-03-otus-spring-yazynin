package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Comment;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}
