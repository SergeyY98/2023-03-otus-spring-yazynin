package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;
import ru.otus.spring.exceptions.AnswerIndexOutOfBoundsException;
import ru.otus.spring.exceptions.DataLoadingException;

@Service
public class ApplicationRunner {
  private final IOService ioService;

  private final TaskService taskService;

  private final TaskConverter taskConverter;

  private final AnswerService answerService;

  private final int passedScore;

  @Autowired
  public ApplicationRunner(IOService ioService, TaskService taskService,
                           TaskConverter taskConverter, AnswerService answerService,
                           int passedScore) {
    this.ioService = ioService;
    this.taskService = taskService;
    this.taskConverter = taskConverter;
    this.answerService = answerService;
    this.passedScore = passedScore;
  }

  public void run(){
    var student = enterStudentCredentials();
    makeTest(student);
  }

  private Student enterStudentCredentials() {
    var credentials = ioService.readStringWithPrompt("Please enter your name and surname")
        .split(" ");
    while (credentials.length < 2) {
      credentials = ioService.readStringWithPrompt("Please enter your name and surname correctly")
          .split(" ");
    }
    return new Student(credentials[0], credentials[1]);
  }

  private void makeTest(Student student) {
    try {
      var index = 0;
      var correctAnswerNum = 0;
      var tasks = taskService.getAll();
      while (index < tasks.size()) {
        var answerNum = ioService.readIntWithPrompt(taskConverter.convertTaskToString(index + 1, tasks.get(index)));
        var answers = tasks.get(index).getAnswers();
        try {
          answerService.checkAnswerNumber(answerNum, answers.size());
          correctAnswerNum += answers.get(answerNum-1).getCorrectness()? 1: 0;
          index++;
        } catch (AnswerIndexOutOfBoundsException e) {
          ioService.outputString("Enter correct number");
        }
      }
      var result = passedScore<=correctAnswerNum?"passed":"failed";
      ioService.outputString("Score: " + correctAnswerNum + "/" + tasks.size());
      ioService.outputString(student.getName() + " " + student.getSurname() + " " + result);
    } catch (DataLoadingException e) {
      ioService.outputString("Data loading error");
    } catch (IllegalArgumentException e) {
      ioService.outputString("Data format error");
    }
  }
}
