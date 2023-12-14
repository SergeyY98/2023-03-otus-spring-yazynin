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
import ru.otus.spring.dto.Response;
import ru.otus.spring.service.FlightSearchService;
import ru.otus.spring.service.ReplyMessagesService;
import ru.otus.spring.service.SendFlightsInfoService;
import ru.otus.spring.telegram.BotState;
import java.time.Duration;

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
    return search(message);
  }

  @Override
  public BotState getHandlerName() {
    return BotState.FLIGHTS_SEARCH;
  }

  private SendMessage search(Message message) {
    String usersAnswer = message.getText();
    long chatId = message.getChatId();
    long userId = message.getFrom().getId();
    log.info("Поиск рейсов по заданным критериям chatId: {}, userId: {}, usersAnswer: {}", chatId, userId, usersAnswer);
    SendMessage replyToUser = messageService.getReplyMessage(String.valueOf(chatId), "reply.query.failed");
    MultiValueMap<String, String> requestData = userDataCache.getUserFlightSearchData(userId);

    BotState botState = userDataCache.getUsersCurrentBotState(userId);
    if (botState.equals(BotState.FLIGHTS_SEARCH)) {
      String[] mainParams = usersAnswer.split(" ");
      if (mainParams.length < 3) {
        return messageService.getReplyMessage(String.valueOf(chatId), "reply.flightSearch.wrongInputFormat");
      }
      requestData.add("fromId", mainParams[0]);
      requestData.add("toId", mainParams[1]);
      requestData.add("departDate", mainParams[2]);
      Mono<Response> data = flightSearchService.getFlightsList(requestData).cache(Duration.ofHours(1));
      sendFlightsInfoService.sendFlightInfo(chatId, data);
      userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
      replyToUser = messageService.getReplyMessage(String.valueOf(chatId), "reply.query.flight.finished");
    }
    return replyToUser;
  }
}
