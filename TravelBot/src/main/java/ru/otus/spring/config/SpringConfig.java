package ru.otus.spring.config;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.spring.telegram.TelegramFacade;
import ru.otus.spring.telegram.TravelBot;

import java.util.Locale;

@Configuration
@AllArgsConstructor
public class SpringConfig {
  private final TravelBotProvider telegramConfig;

  @Bean
  public TravelBot travelBot(TelegramFacade telegramFacade) {
    TravelBot bot = new TravelBot(telegramFacade);
    bot.setBotPath(telegramConfig.getWebhookPath());
    bot.setBotUsername(telegramConfig.getBotName());
    bot.setBotToken(telegramConfig.getBotToken());

    return bot;
  }
}
