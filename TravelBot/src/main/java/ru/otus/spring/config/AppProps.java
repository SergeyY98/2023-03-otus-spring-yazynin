package ru.otus.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
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
  public void setApiUrl(String apiUrl) {
    this.apiUrl = apiUrl;
  }

  @Override
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  @Override
  public void setApiHost(String apiHost) {
    this.apiHost = apiHost;
  }

  @Override
  public void setBotName(String botName) {
    this.botName = botName;
  }

  @Override
  public void setBotToken(String botToken) {
    this.botToken = botToken;
  }

  @Override
  public void setWebhookPath(String webhookPath) {
    this.webhookPath = webhookPath;
  }

  @Override
  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}
