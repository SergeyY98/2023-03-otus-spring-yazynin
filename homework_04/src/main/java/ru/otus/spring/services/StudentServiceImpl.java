package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

  private final IOService ioService;

  private final MessageService messageService;

  @Autowired
  public StudentServiceImpl(IOService ioService, MessageService messageService) {
    this.ioService = ioService;
    this.messageService = messageService;
  }

  @Override
  public Student createStudent() {
    boolean loop = true;
    String name = "";
    String surname = "";
    while (loop) {
      try {
        var credentials = ioService.readStringWithPrompt(
                messageService.getMessage("getCredentialsMsg")).split(" ");
        name = credentials[0];
        surname = credentials[1];
        loop = false;
      } catch (ArrayIndexOutOfBoundsException e) {
        ioService.outputString(messageService.getMessage("wrongCredentialsMsg"));
      }
    }
    return new Student(name, surname);
  }
}
