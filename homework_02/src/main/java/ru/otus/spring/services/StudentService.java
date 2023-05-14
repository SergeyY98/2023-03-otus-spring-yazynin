package ru.otus.spring.services;

import ru.otus.spring.domain.Student;

public interface StudentService {
  Student createStudent();

  void checkStudentResult(Student student, int score);
}
