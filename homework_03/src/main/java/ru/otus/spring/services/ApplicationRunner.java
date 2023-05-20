package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Result;
import ru.otus.spring.domain.Student;
import ru.otus.spring.exceptions.DataLoadingException;

@Service
public class ApplicationRunner {
  private final IOService ioService;

  private final TaskService taskService;

  private final StudentService studentService;

  private final ResultService resultService;

  @Autowired
  public ApplicationRunner(IOService ioService, TaskService taskService,
                           StudentService studentService, ResultService resultService) {
    this.ioService = ioService;
    this.taskService = taskService;
    this.studentService = studentService;
    this.resultService = resultService;
  }

  public void run(){
    var student = studentService.createStudent();
    var result = makeTest(student);
    resultService.print(result);
  }

  private Result makeTest(Student student) {
    var score = 0;
    try {
      var tasks = taskService.getAll();
      for (var i = 0; i < tasks.size(); i++) {
        var task = tasks.get(i);
        score += taskService.getTaskResult(i, task);
      }
    } catch (DataLoadingException e) {
      ioService.outputString("Data loading error");
    } catch (IllegalArgumentException e) {
      ioService.outputString("Data format error");
    }
    return new Result(student, score);
  }
}
