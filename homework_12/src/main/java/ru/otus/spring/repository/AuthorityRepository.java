package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
