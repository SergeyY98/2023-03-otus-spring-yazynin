package ru.otus.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.configs.ApplicationConfigTest;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Task;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Adding new task")
@SpringBootTest
@ContextConfiguration(classes= ApplicationConfigTest.class)
public class TaskServiceImplTest {

  @MockBean
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
    given(taskService.getAll())
        .willReturn(task);
    var tasks = taskService.getAll();
    assertThat(tasks).containsExactlyElementsOf(task);
  }
}
