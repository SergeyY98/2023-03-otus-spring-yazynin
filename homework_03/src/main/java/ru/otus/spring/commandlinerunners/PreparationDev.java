package ru.otus.spring.commandlinerunners;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.spring.services.ApplicationRunner;

@Component
public class PreparationDev implements CommandLineRunner {
  private final ApplicationRunner applicationRunner;

  public PreparationDev(ApplicationRunner applicationRunner) {
    this.applicationRunner = applicationRunner;
  }

  @Override
  public void run(String... args) {
    applicationRunner.run();
  }

}