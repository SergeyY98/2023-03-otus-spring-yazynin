package ru.otus.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties(prefix = "telegram")
@Data
public class AppProps implements TravelApiProvider, TravelBotProvider, LocaleProvider {
  private String apiUrl;

  private String apiKey;

  private String apiHost;

  private String userName;

  private String botName;

  private String botToken;

  private String webhookPath;

  private Locale locale;

  @Override
  public String getApiUrl() {
    return apiUrl;
  }

  @Override
  public String getApiKey() {
    return apiKey;
  }

  @Override
  public String getApiHost() {
    return apiHost;
  }

  @Override
  public String getBotName() {
    return botName;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  @Override
  public String getWebhookPath() {
    return webhookPath;
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
