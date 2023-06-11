package ru.otus.spring.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties(prefix = "application")
public class AppProps implements FileNameProvider, LocaleProvider, LimitScoreProvider {

  private String fileName;

  private int limitScore;

  private Locale locale;

  @Override
  public int getLimitScore() {
    return limitScore;
  }

  @Override
  public void setLimitScore(int limitScore) {
    this.limitScore = limitScore;
  }

  @Override
  public String getFileName() {
    if (!locale.toString().equals("")) {
      fileName = fileName.replace(".", "_" + locale + ".");
    }
    return fileName;
  }

  @Override
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public Locale getLocale() {
    return locale;
  }

  @Override
  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}
