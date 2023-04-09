package ru.otus.spring.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Task;
import ru.otus.spring.services.processors.InputService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Adding new task")
@ExtendWith(MockitoExtension.class)
public class TaskServiceImpl {

  @Mock
  private InputService inputService;

  @Mock
  private TaskService taskService;

  @DisplayName("Must add new task correctly")
  @Test
  void shouldCorrectAddExpectedNoteUseExpectedServicesMethods() {
    var expectedQuestionText = "Expected Question Text";
    given(inputService.readString())
        .willReturn(expectedQuestionText);

    verify(inputService, times(1)).readString();

    var captor = ArgumentCaptor.forClass(Task.class);
    verify(taskService).save(captor.capture());
    var actualTask = captor.getValue();
    assertThat(actualTask).extracting(Task::getQuestion).isEqualTo(expectedQuestionText);
  }
}