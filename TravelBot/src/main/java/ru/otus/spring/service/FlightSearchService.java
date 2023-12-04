package ru.otus.spring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import ru.otus.spring.config.TravelApiProvider;
import ru.otus.spring.dto.Flight;
import ru.otus.spring.dto.ResponseData;

import java.time.Duration;

@Service
public class FlightSearchService {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private final TravelApiProvider provider;

  @Autowired
  public FlightSearchService(TravelApiProvider provider) { this.provider = provider; }

  public Mono<ResponseData> getFlightsList(MultiValueMap<String, String> params) {
    WebClient webClient = WebClient.builder()
        .baseUrl(provider.getApiUrl())
        .defaultHeader("X-RapidAPI-Key", provider.getApiKey())
        .defaultHeader("X-RapidAPI-Host", provider.getApiHost())
        .build();

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/flights/searchFlights")
            .queryParams(params)
            .build())
        .retrieve()
        .bodyToMono(ResponseData.class)
        .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)));
  }

  public Mono<Flight> getFlightDetails(String token) {
    WebClient webClient = WebClient.builder()
        .baseUrl(provider.getApiUrl())
        .defaultHeader("X-RapidAPI-Key", provider.getApiKey())
        .defaultHeader("X-RapidAPI-Host", provider.getApiHost())
        .build();

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/v1/flights/getFlightDetails")
            .queryParam("token", token)
            .build())
        .retrieve()
        .bodyToMono(Flight.class)
        .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(100)));
  }
}
