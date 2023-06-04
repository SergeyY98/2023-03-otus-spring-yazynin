package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.services.ApplicationRunner;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommand {

  private final ApplicationRunner applicationRunner;

  @ShellMethod(value = "Run survey", key = {"r", "run"})
  public void run() {
    applicationRunner.run();
  }
}

