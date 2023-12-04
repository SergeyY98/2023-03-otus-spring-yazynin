package ru.otus.spring.config;

public interface TravelApiProvider {
  String getApiUrl();

  String getApiKey();

  String getApiHost();
}
