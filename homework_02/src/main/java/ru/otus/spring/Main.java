package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.services.ApplicationRunner;

@Configuration
@ComponentScan
public class Main {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext context =
        new AnnotationConfigApplicationContext(Main.class);
    var applicationRunner = context.getBean(ApplicationRunner.class);
    applicationRunner.run();
  }
}
