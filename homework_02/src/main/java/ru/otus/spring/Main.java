package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.services.ApplicationRunner;

@PropertySource("classpath:application.properties")
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
