package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.dao.TaskDao;
import ru.otus.spring.dao.TaskDaoImpl;
import ru.otus.spring.services.ApplicationRunner;
import ru.otus.spring.services.AnswerService;
import ru.otus.spring.services.TaskService;
import ru.otus.spring.services.TaskConverter;
import ru.otus.spring.services.IOService;

@PropertySource("classpath:application.properties")
@Configuration
public class AppConfig {

  @Bean
  public TaskDao taskDao(@Value("${resource.name}") String fileName, TaskConverter taskConverter) {
    return new TaskDaoImpl(fileName, taskConverter);
  }

  @Bean
  public ApplicationRunner applicationRunner(IOService ioService, TaskService taskService,
                                             TaskConverter taskConverter, AnswerService answerService,
                                             @Value("${passed.score}") int passedScore) {
    return new ApplicationRunner(ioService, taskService, taskConverter, answerService, passedScore);
  }
}
