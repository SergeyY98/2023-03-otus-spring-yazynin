package ru.otus.spring.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.dao.TaskDao;
import ru.otus.spring.dao.TaskDaoImpl;
import ru.otus.spring.services.TaskConverter;

@Configuration
@EnableConfigurationProperties(AppProps.class)
public class ApplicationConfig {

  private final AppProps appProps;

  public ApplicationConfig(AppProps appProps) {
    this.appProps = appProps;
  }

  @Bean
  public TaskDao taskDao(TaskConverter taskConverter) {
    return new TaskDaoImpl(new ClassPathResource(appProps.getFileName()), taskConverter);
  }
}
