package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.spring.cache.UserDataCache;
import ru.otus.spring.dto.ResponseData;
import ru.otus.spring.telegram.TravelBot;
import ru.otus.spring.utils.Emojis;

@Service
public class SendFlightsInfoService {
  final private TravelBot travelBot;
  final private ReplyMessagesService messagesService;

  @Autowired
  public SendFlightsInfoService(ReplyMessagesService messagesService,
                                @Lazy TravelBot travelBot) {
    this.messagesService = messagesService;
    this.travelBot = travelBot;
  }


  public void sendFlightInfo(long chatId, Mono<ResponseData> flights) {
    flights.subscribe(flightList -> {
      flightList.getFlightOffers().forEach(flight -> {
        String flightTicketsInfoMessage = messagesService.getReplyText("reply.flightSearch.flightInfo",
            Emojis.PLAIN_DEPARTURE.toString(), flight.getSegments().get(0).getDepartureAirport().getCityName(),
            flight.getSegments().get(0).getDepartureTime(), Emojis.PLAIN_DEPARTURE.toString(),
            flight.getSegments().get(0).getArrivalAirport().getCityName(), flight.getSegments().get(0).getArrivalTime(),
            flight.getPriceBreakdown().getTotal().getUnits().toString());
        travelBot.sendInlineKeyBoardMessage(chatId, flightTicketsInfoMessage, "Подписаться", flight.getToken());
        });
      });
  }


}

