package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Task;
import ru.otus.spring.exceptions.AnswerIndexOutOfBoundsException;
import ru.otus.spring.exceptions.DataLoadingException;

@Service
public class ApplicationRunner {
  private final IOService ioService;

  private final TaskService taskService;

  private final AnswerService answerService;

  private final TaskConverter taskConverter;

  private final StudentService studentService;

  @Autowired
  public ApplicationRunner(IOService ioService, TaskService taskService,
                           AnswerService answerService, TaskConverter taskConverter,
                           StudentService studentService) {
    this.ioService = ioService;
    this.taskService = taskService;
    this.answerService = answerService;
    this.taskConverter = taskConverter;
    this.studentService = studentService;
  }

  public void run(){
    var student = studentService.createStudent();
    var score = makeTest();
    studentService.returnStudentResult(student, score);
  }

  private int getTaskResult(int index, Task task) {
    var taskString = taskConverter.convertTaskToString(index + 1, task);
    var answers = task.getAnswers();
    boolean loop = true;
    var answerNum = 1;
    while (loop) {
      try {
        answerNum = ioService.readIntWithPrompt(taskString);
        answerService.checkAnswerNumber(answerNum, answers.size());
        loop = false;
      } catch (AnswerIndexOutOfBoundsException e) {
        ioService.outputString("Enter correct number");
      }
    }
    return answers.get(answerNum-1).getCorrectness()?1:0;
  }

  private int makeTest() {
    var score = 0;
    try {
      var tasks = taskService.getAll();
      for (var i = 0; i < tasks.size(); i++) {
        var task = tasks.get(i);
        score += getTaskResult(i, task);
      }
    } catch (DataLoadingException e) {
      ioService.outputString("Data loading error");
    } catch (IllegalArgumentException e) {
      ioService.outputString("Data format error");
    }
    return score;
  }
}
