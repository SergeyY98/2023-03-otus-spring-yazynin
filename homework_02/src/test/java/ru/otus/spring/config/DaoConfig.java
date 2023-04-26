package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:test.properties")
@Configuration
public class DaoConfig {

  @Value("${resource.name}")
  private String fileName;

  @Value("${passed.score}")
  private int passedScore;

  @Bean
  public String getFileName() {
    return fileName;
  }

  @Bean
  public int getPassedScore() {
    return passedScore;
  }
}