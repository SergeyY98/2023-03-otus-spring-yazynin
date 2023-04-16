package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.services.ApplicationRunner;


public class Main {
  public static void main(String[] args) {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("spring-context.xml");
    var applicationRunner = context.getBean(ApplicationRunner.class);
    applicationRunner.run();
  }
}
