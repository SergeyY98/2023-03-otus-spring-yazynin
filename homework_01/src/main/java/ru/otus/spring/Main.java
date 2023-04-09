package ru.otus.spring;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.services.ApplicationRunner;
import ru.otus.spring.services.IOServiceStreams;
import ru.otus.spring.services.TaskConverterImpl;
import ru.otus.spring.services.TaskServiceImpl;


public class Main {
  public static void main(String[] args) {
    ApplicationContext context =
        new ClassPathXmlApplicationContext("spring-context.xml");
    var resource = context.getBean(ClassPathResource.class);
    var tasksService = context.getBean(TaskServiceImpl.class);
    var taskConverter = context.getBean(TaskConverterImpl.class);

    try {
      InputStream is = resource.getInputStream();
      var ioService = new IOServiceStreams(System.out, is);
      var applicationRunner = context.getBean(TaskConverterImpl.class);
      new ApplicationRunner(ioService, tasksService, taskConverter)
          .run();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
