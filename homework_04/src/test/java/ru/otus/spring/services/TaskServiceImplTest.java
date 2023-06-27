package ru.otus.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.TaskDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Task;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Adding new task")
@SpringBootTest
public class TaskServiceImplTest {

  @Autowired
  private TaskService taskService;

  @MockBean
  private TaskDao taskDao;

  @DisplayName("должен возвращать ожидаемое задание")
  @Test
  void shouldReturnTask() {
    var question = "test_question";
    List<Answer> answers = new ArrayList<>();
    answers.add(new Answer("Correct answer", true));
    answers.add(new Answer("Incorrect answer 1", false));
    answers.add(new Answer("Incorrect answer 2", false));
    List<Task> task = new ArrayList<>();
    task.add(new Task(question, answers));
    given(taskService.getAll())
        .willReturn(task);
    var tasks = taskService.getAll();
    assertThat(tasks).containsExactlyElementsOf(task);
  }
}
