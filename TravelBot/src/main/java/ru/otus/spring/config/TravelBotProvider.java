package ru.otus.spring.config;

public interface TravelBotProvider {
  String getBotName();

  String getBotToken();

  String getWebhookPath();

  void setBotName(String botName);

  void setBotToken(String botToken);

  void setWebhookPath(String webhookPath);
}
