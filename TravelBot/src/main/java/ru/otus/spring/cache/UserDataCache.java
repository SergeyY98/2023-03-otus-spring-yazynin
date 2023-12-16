package ru.otus.spring.cache;

import org.springframework.stereotype.Service;
import ru.otus.spring.dto.Flight;
import ru.otus.spring.telegram.BotState;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserDataCache implements DataCache {
  private final Map<Long, BotState> usersBotStates = new HashMap<>();

  private final Map<Long, List<Flight>> searchFoundedFlights = new HashMap<>();

  @Override
  public void setUsersCurrentBotState(long userId, BotState botState) {
    usersBotStates.put(userId, botState);
  }

  @Override
  public BotState getUsersCurrentBotState(long userId) {
    BotState botState = usersBotStates.get(userId);
    if (botState == null) {
      botState = BotState.FLIGHTS_SEARCH;
    }

    return botState;
  }

  @Override
  public void saveSearchFoundedFlights(long chatId, List<Flight> foundFlights) {
    searchFoundedFlights.put(chatId, foundFlights);
  }

  @Override
  public List<Flight> getSearchFoundedFlights(long chatId) {
    List<Flight> foundedFlights = searchFoundedFlights.get(chatId);

    return Objects.isNull(foundedFlights) ? Collections.emptyList() : foundedFlights;
  }

}
