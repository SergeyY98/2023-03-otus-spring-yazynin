package ru.otus.spring.config;

public interface TravelBotProvider {
  String getBotName();

  String getBotToken();

  String getWebhookPath();
}
