package ru.otus.spring.telegram;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.service.FlightSubscriptionService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class WebhookController {

  private final TravelBot travelBot;

  private final FlightSubscriptionService subscriptionService;

  @PostMapping("/")
  public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
    log.info("Update received: {}", update);
    return travelBot.onWebhookUpdateReceived(update);
  }

  @GetMapping(value = "/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<FlightSubscription> index() {
    return subscriptionService.getAllSubscriptions();
  }
}
