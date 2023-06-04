package ru.otus.spring.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.dao.TaskDao;
import ru.otus.spring.dao.TaskDaoImpl;
import ru.otus.spring.services.TaskConverter;
import ru.otus.spring.services.TaskConverterImpl;

@Configuration
public class ApplicationConfigTest {

  @Bean
  public TaskDao taskDao(@Value("${fileName}") String fileName, TaskConverter taskConverter) {
    return new TaskDaoImpl(new ClassPathResource(fileName), taskConverter);
  }

  @Bean
  public TaskConverter taskConverter() {
    return new TaskConverterImpl();
  }
}