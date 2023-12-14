package ru.otus.spring.config;

public interface TravelApiProvider {
  String getApiUrl();

  String getApiKey();

  String getApiHost();

  void setApiUrl(String apiUrl);

  void setApiKey(String apiKey);

  void setApiHost(String apiHost);
}
