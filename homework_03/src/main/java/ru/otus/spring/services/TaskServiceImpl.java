package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.TaskDao;
import ru.otus.spring.domain.Task;
import ru.otus.spring.exceptions.AnswerIndexOutOfBoundsException;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskDao taskDao;

  private final IOService ioService;

  private final AnswerService answerService;

  private final TaskConverter taskConverter;

  private final MessageService messageService;

  @Autowired
  public TaskServiceImpl(TaskDao taskDao, IOService ioService,
                         AnswerService answerService, TaskConverter taskConverter,
                         MessageService messageService) {
    this.taskDao = taskDao;
    this.ioService = ioService;
    this.answerService = answerService;
    this.taskConverter = taskConverter;
    this.messageService = messageService;
  }

  @Override
  public List<Task> getAll() {
    return taskDao.getAll();
  }

  @Override
  public int getTaskResult(int index, Task task) {
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
        ioService.outputString(messageService.getMessage("answerIndexOutOfBoundsMsg"));
      }
    }
    return answers.get(answerNum-1).getCorrectness()?1:0;
  }
}
