package ru.otus.spring.cache;

import org.springframework.util.LinkedMultiValueMap;
import ru.otus.spring.dto.Flight;
import ru.otus.spring.telegram.BotState;

import java.util.List;

public interface DataCache {
  void setUsersCurrentBotState(long userId, BotState botState);

  BotState getUsersCurrentBotState(long userId);

  void saveFlightSearchData(long userId, LinkedMultiValueMap<String, String> FlightSearchData);

  LinkedMultiValueMap<String, String> getUserFlightSearchData(long userId);

  void saveSearchFoundedFlights(long chatId, List<Flight> foundFlights);

  List<Flight> getSearchFoundedFlights(long chatId);
}
