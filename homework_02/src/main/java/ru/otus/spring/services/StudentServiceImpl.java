package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

  private final IOService ioService;

  @Autowired
  public StudentServiceImpl(IOService ioService) {
    this.ioService = ioService;
  }

  @Override
  public Student createStudent() {
    boolean loop = true;
    String name = "";
    String surname = "";
    while (loop) {
      try {
        var credentials = ioService.readStringWithPrompt("Please enter your name and surname")
            .split(" ");
        name = credentials[0];
        surname = credentials[1];
        loop = false;
      } catch (ArrayIndexOutOfBoundsException e) {
        ioService.outputString("Credentials are incorrect");
      }
    }
    return new Student(name, surname);
  }
}
