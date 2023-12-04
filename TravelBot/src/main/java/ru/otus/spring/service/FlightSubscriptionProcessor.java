package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.Flight;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.telegram.TravelBot;
import ru.otus.spring.utils.Emojis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class FlightSubscriptionProcessor {
  private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
  private final FlightSubscriptionService subscriptionService;
  private final FlightSearchService flightSearchService;
  private final ReplyMessagesService messageService;
  private final TravelBot travelBot;

  @Autowired
  public FlightSubscriptionProcessor(FlightSubscriptionService subscriptionService,
                                        FlightSearchService flightSearchService,
                                        ReplyMessagesService messageService,
                                        TravelBot travelBot) {
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
    Mono<Flight> actualFlight = flightSearchService.getFlightDetails(subscription.getToken());
    subscriptionService.saveUserSubscription(subscription);

    String notificationMessage = messageService.getReplyText("subscription.flightTicketsPriceChanges",
        Emojis.NOTIFICATION_BELL.toString(), subscription.getSegments().get(0).getDepartureCityName(),
        subscription.getSegments().get(0).getArrivalCityName(), subscription.getSegments().get(0).getDepartureTime(),
        subscription.getSegments().get(0).getArrivalTime()) + messageService.getReplyText("subscription.lastTicketPrices");

    travelBot.sendMessage(subscription.getChatId(), notificationMessage);
  }

  private Date parseDateDeparture(String dateDeparture) {
    Date dateDepart = null;
    try {
      dateDepart = DATE_FORMAT.parse(dateDeparture);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return dateDepart;
  }
}
