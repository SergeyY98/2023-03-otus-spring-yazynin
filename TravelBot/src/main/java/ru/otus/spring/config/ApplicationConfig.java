package ru.otus.spring.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import ru.otus.spring.telegram.TelegramFacade;
import ru.otus.spring.telegram.TravelBot;

@Configuration
@EnableConfigurationProperties(AppProps.class)
public class ApplicationConfig {

  private final AppProps appProps;

  public ApplicationConfig(AppProps appProps) {
    this.appProps = appProps;
  }

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .baseUrl("https://" + appProps.getApiHost() + appProps.getApiUrl())
        .defaultHeader("X-RapidAPI-Key", appProps.getApiKey())
        .defaultHeader("X-RapidAPI-Host", appProps.getApiHost())
        .build();
  }

  @Bean
  public TravelBot travelBot(TelegramFacade telegramFacade) {
    TravelBot travelBot = new TravelBot(telegramFacade);
    travelBot.setBotUsername(appProps.getBotName());
    travelBot.setBotToken(appProps.getBotToken());
    travelBot.setBotPath(appProps.getWebhookPath());

    return travelBot;
  }
}

