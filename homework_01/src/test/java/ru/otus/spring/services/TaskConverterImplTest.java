package ru.otus.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Task;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Adding new task")
@ExtendWith(MockitoExtension.class)
public class TaskConverterImplTest {

  private TaskConverterImpl taskConverter;

  @BeforeEach
  void setUp() {
    taskConverter = new TaskConverterImpl();
  }

  @DisplayName("Must convert task to string correctly")
  @Test
  void shouldCorrectConvertTaskToString() {
    String question = "A";
    String[] answers = {"B", "C", "D", "E"};
    Task task = new Task(question, answers);
    assertThat(taskConverter.convertTaskToString(1, task)).startsWith("1");
  }
}