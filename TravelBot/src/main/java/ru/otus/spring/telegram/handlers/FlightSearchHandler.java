package ru.otus.spring.telegram.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import reactor.core.publisher.Mono;
import ru.otus.spring.cache.UserDataCache;
import ru.otus.spring.dto.ResponseData;
import ru.otus.spring.service.FlightSearchService;
import ru.otus.spring.service.ReplyMessagesService;
import ru.otus.spring.service.SendFlightsInfoService;
import ru.otus.spring.telegram.BotState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class FlightSearchHandler implements InputMessageHandler {

  private final UserDataCache userDataCache;
  private final FlightSearchService flightSearchService;
  private final SendFlightsInfoService sendFlightsInfoService;
  private final ReplyMessagesService messageService;

  @Autowired
  public FlightSearchHandler(UserDataCache userDataCache, FlightSearchService flightSearchService,
                             ReplyMessagesService messageService, SendFlightsInfoService sendFlightsInfoService) {
    this.userDataCache = userDataCache;
    this.flightSearchService = flightSearchService;
    this.sendFlightsInfoService = sendFlightsInfoService;
    this.messageService = messageService;
  }

  @Override
  public SendMessage handle(Message message) {
    if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FLIGHTS_SEARCH)) {
      userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_SEARCH_PARAMS);
    }
    return search(message);
  }

  @Override
  public BotState getHandlerName() {
    return BotState.FLIGHTS_SEARCH;
  }

  private SendMessage search(Message message) {
    String usersAnswer = message.getText();
    String chatId = String.valueOf(message.getChatId());
    long userId = message.getFrom().getId();
    SendMessage replyToUser = messageService.getReplyMessage(chatId, "Не могу обработать ваш запрос, повторите с начала");
    MultiValueMap<String, String> requestData = userDataCache.getUserFlightSearchData(userId);

    BotState botState = userDataCache.getUsersCurrentBotState(userId);
    if (botState.equals(BotState.FLIGHTS_SEARCH)) {
      replyToUser = messageService.getReplyMessage(chatId, "Введите аэропорты отправления, прибытия и дату вылета");
      userDataCache.setUsersCurrentBotState(userId, BotState.ASK_SEARCH_PARAMS);
    }
    if (botState.equals(BotState.ASK_SEARCH_PARAMS)) {
      String[] mainParams = usersAnswer.split(" ");
      log.info("Поиск рейсов по заданным критериям fromId: {}, toId: {}, departureDate: {}",
          mainParams[0], mainParams[1], mainParams[2]);
      Date dateDepart;
      try {
        dateDepart = new SimpleDateFormat("dd-MM-yyyy").parse(mainParams[2]);
      } catch (ParseException e) {
        return messageService.getReplyMessage(chatId, "Неверный формат даты, повторите ввод в формате dd-MM-yyyy");
      }
      requestData.add("fromId", mainParams[0]);
      requestData.add("toId", mainParams[1]);
      requestData.add("departureDate", mainParams[2]);

      Mono<ResponseData> flights = flightSearchService.getFlightsList(requestData).cache(Duration.ofHours(1));
      sendFlightsInfoService.sendFlightInfo(Long.parseLong(chatId), flights);
      userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
    }
    log.info("Статус пользователя {} изменился на {}", userId, userDataCache.getUsersCurrentBotState(userId));
    return replyToUser;
  }
}
