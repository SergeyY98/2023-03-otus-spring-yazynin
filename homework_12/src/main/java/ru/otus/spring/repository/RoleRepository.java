package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
