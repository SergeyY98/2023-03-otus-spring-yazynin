package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {

  private final IOService ioService;

  private final MessageSource messageSource;

  private final AppProps props;

  @Autowired
  public StudentServiceImpl(IOService ioService, MessageSource messageSource, AppProps props) {
    this.ioService = ioService;
    this.messageSource = messageSource;
    this.props = props;
  }

  @Override
  public Student createStudent() {
    boolean loop = true;
    String name = "";
    String surname = "";
    while (loop) {
      try {
        var credentials = ioService.readStringWithPrompt(
            messageSource.getMessage("getCredentials", new String[]{}, props.getLocale()))
            .split(" ");
        name = credentials[0];
        surname = credentials[1];
        loop = false;
      } catch (ArrayIndexOutOfBoundsException e) {
        ioService.outputString(messageSource.getMessage("wrongCredentials", new String[]{}, props.getLocale()));
      }
    }
    return new Student(name, surname);
  }
}
