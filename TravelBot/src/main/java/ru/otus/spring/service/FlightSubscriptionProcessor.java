package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.dto.ResponseDetails;
import ru.otus.spring.telegram.TravelBot;
import ru.otus.spring.utils.Emojis;

@Slf4j
@Service
public class FlightSubscriptionProcessor {
  private final FlightSubscriptionService subscriptionService;

  private final FlightSearchService flightSearchService;

  private final ReplyMessagesService messageService;

  private final TravelBot travelBot;

  @Autowired
  public FlightSubscriptionProcessor(FlightSubscriptionService subscriptionService,
                                        FlightSearchService flightSearchService,
                                        ReplyMessagesService messageService,
                                        @Lazy TravelBot travelBot) {
    this.subscriptionService = subscriptionService;
    this.flightSearchService = flightSearchService;
    this.messageService = messageService;
    this.travelBot = travelBot;
  }


  /**
   * Периодически смотрит за обновлением цен
   * по всей базе подписок.
   */
  @Scheduled(fixedRateString = "${subscriptions.processPeriod}")
  public void processAllUsersSubscriptions() {
    log.info("Выполняю обработку подписок пользователей.");
    subscriptionService.getAllSubscriptions().forEach(this::processSubscription);
    log.info("Завершил обработку подписок пользователей.");
  }

  /**
   * Получает актуальные данные по билетам для текущей подписки,
   * если цена изменилась сохраняет последнюю и уведомляет клиента.
   */
  private void processSubscription(FlightSubscription subscription) {
    Mono<ResponseDetails> actualFlight = flightSearchService.getFlightDetails(subscription.getToken());
    actualFlight.subscribe(flight -> {
      log.info("Получил данные по билетам {} по токену {}", flight, subscription.getToken());
      if ((double)flight.getData().getPriceBreakdown().getTotal().getUnits() /
          subscription.getPriceBreakdown().getTotal().getUnits() <= 0.9) {
        String notificationMessage = messageService.getReplyText("subscription.flightTicketsPriceChanges",
            Emojis.NOTIFICATION_MARK.toString(), subscription.getOfferKeyToHighlight(),
            subscription.getPriceBreakdown().getTotal().getUnits().toString(), Emojis.ARROW_RIGHT.toString(),
            flight.getData().getPriceBreakdown().getTotal().getUnits().toString(),
            subscription.getPriceBreakdown().getTotal().getCurrencyCode().toString());

        subscription.setPriceBreakdown(flight.getData().getPriceBreakdown());
        subscriptionService.saveUserSubscription(subscription);

        travelBot.sendMessage(subscription.getChatId(), notificationMessage);
      }
    });
    subscriptionService.saveUserSubscription(subscription);
  }
}
