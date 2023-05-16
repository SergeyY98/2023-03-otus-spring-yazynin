package ru.otus.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.TaskDaoImpl;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Task;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Adding new task")
@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

  @Mock
  private TaskDaoImpl taskDao;

  @InjectMocks
  private TaskServiceImpl taskService;

  @DisplayName("должен возвращает ожидаеме задание")
  @Test
  void shouldReturnTask() {
    var question = "test_question";
    List<Answer> answers = new ArrayList<>();
    answers.add(new Answer("Correct answer", true));
    answers.add(new Answer("Incorrect answer 1", false));
    answers.add(new Answer("Incorrect answer 2", false));
    List<Task> task = new ArrayList<>();
    task.add(new Task(question, answers));
    given(taskDao.getAll())
        .willReturn(task);

    var tasks = taskService.getAll();
      assertThat(tasks).containsExactlyElementsOf(task);
  }
}
