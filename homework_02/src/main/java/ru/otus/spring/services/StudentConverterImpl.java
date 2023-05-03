package ru.otus.spring.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

import java.util.Arrays;
import java.util.List;

@Service
public class StudentConverterImpl implements StudentConverter {

  @Override
  public Student convertStringToStudent(final String from) {
    List<String> credentials = Arrays.asList(from.split(" "));
    return new Student(credentials.get(0), credentials.get(1));
  }
}
