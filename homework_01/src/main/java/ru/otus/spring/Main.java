package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.services.ApplicationRunner;
import ru.otus.spring.services.IOServiceStreams;
import ru.otus.spring.services.TaskConverterImpl;
import ru.otus.spring.services.TaskServiceImpl;


public class Main {
  public static void main(String[] args) {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("spring-context.xml");
    var ioService = context.getBean(IOServiceStreams.class);
    var tasksService = context.getBean(TaskServiceImpl.class);
    var taskConverter = context.getBean(TaskConverterImpl.class);
    new ApplicationRunner(ioService, tasksService, taskConverter)
          .run();
  }
}
