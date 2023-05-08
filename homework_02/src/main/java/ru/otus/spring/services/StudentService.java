package ru.otus.spring.services;

import ru.otus.spring.domain.Student;

public interface StudentService {
  Student createStudent();

  void returnStudentResult(Student student, int score);
}
