package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.dao.TaskDao;
import ru.otus.spring.dao.TaskDaoImpl;
import ru.otus.spring.services.TaskConverter;

@PropertySource("classpath:application.properties")
@Configuration
public class DaoConfig {

  @Bean
  public TaskDao taskDao(@Value("${resource.name}") String fileName, TaskConverter taskConverter,
                         @Value("${passed.score}") int passedScore) {
    return new TaskDaoImpl(fileName, taskConverter, passedScore);
  }
}
