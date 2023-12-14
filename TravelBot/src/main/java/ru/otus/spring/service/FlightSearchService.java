package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.otus.spring.dto.Response;
import ru.otus.spring.dto.ResponseDetails;

@Slf4j
@Service
public class FlightSearchService {

  private final WebClient webClient;

  @Autowired
  public FlightSearchService(WebClient webClient) {
    this.webClient = webClient;
  }

  public Mono<Response> getFlightsList(MultiValueMap<String, String> params) {
    return webClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/searchFlights")
            .queryParams(params)
            .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(Response.class)
        .log();
  }

  public Mono<ResponseDetails> getFlightDetails(String token) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/getFlightDetails")
            .queryParam("token", token)
            .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(ResponseDetails.class)
        .log();
  }
}
