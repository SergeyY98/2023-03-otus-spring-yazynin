package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.spring.cache.UserDataCache;
import ru.otus.spring.dto.Flight;
import ru.otus.spring.dto.Response;
import ru.otus.spring.telegram.TravelBot;
import ru.otus.spring.telegram.handlers.callbackquery.CallbackQueryType;
import ru.otus.spring.utils.Emojis;

import java.util.List;

@Slf4j
@Service
public class SendFlightsInfoService {
  private final FlightSubscriptionService subscriptionService;

  private final UserDataCache userDataCache;

  private final ReplyMessagesService messageService;

  private final TravelBot travelBot;

  @Autowired
  public SendFlightsInfoService(FlightSubscriptionService subscriptionService, UserDataCache userDataCache,
                                ReplyMessagesService messageService, @Lazy TravelBot travelBot) {
    this.subscriptionService = subscriptionService;
    this.userDataCache = userDataCache;
    this.messageService = messageService;
    this.travelBot = travelBot;
  }


  public void sendFlightInfo(long chatId, Mono<Response> responseData) {
    responseData.subscribe(response -> {
      log.info("Status: {}, Response: {}", response.getStatus(), response.getMessage());
      List<Flight> flightList = response.getData().getFlightOffers();
      flightList.forEach(flight -> {
        String flightTicketsInfoMessage = messageService.getReplyText("reply.flight.infoMessage",
            Emojis.INFORMATION_SOURCE.toString(), flight.getOfferKeyToHighlight().split("_")[1],
            Emojis.PLAIN_DEPARTURE.toString(), flight.getSegments().get(0).getDepartureAirport().getCityName(),
            Emojis.DATE.toString(), flight.getSegments().get(0).getDepartureTime(),
            Emojis.PLAIN_ARRIVAL.toString(), flight.getSegments().get(0).getArrivalAirport().getCityName(),
            Emojis.DATE.toString(), flight.getSegments().get(0).getArrivalTime(),
            Emojis.MONEY.toString(), flight.getPriceBreakdown().getTotal().getUnits().toString(),
            flight.getPriceBreakdown().getTotal().getCurrencyCode().toString());

        log.info(flightTicketsInfoMessage);
        String subscriptionInfoData = String.format("%s|%s", CallbackQueryType.SUBSCRIBE.name(),
            flight.getOfferKeyToHighlight());
        travelBot.sendInlineKeyBoardMessage(chatId, flightTicketsInfoMessage,
            Emojis.ALARM_CLOCK + " Подписаться", subscriptionInfoData);
      });
      userDataCache.saveSearchFoundedFlights(chatId, flightList);
    });
  }


}

