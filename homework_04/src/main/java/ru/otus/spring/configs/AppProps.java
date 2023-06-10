package ru.otus.spring.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties(prefix = "application")
public class AppProps {

  private String fileName;

  private int limitScore;

  private Locale locale;

  public int getLimitScore() {
    return limitScore;
  }

  public void setLimitScore(int limitScore) {
    this.limitScore = limitScore;
  }

  public String getFileName() {
    if (!locale.toString().equals("")) {
      fileName = fileName.replace(".", "_" + locale + ".");
    }
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Locale getLocale() {
    return locale;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}
