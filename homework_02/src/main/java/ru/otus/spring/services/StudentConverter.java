package ru.otus.spring.services;

import ru.otus.spring.domain.Student;

public interface StudentConverter {
  Student convertStringToStudent(String from);
}
