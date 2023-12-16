package ru.otus.spring.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
  @JsonProperty("flightOffers")
  private List<Flight> flightOffers;

  @JsonCreator
  public Data(@JsonProperty List<Flight> flightOffers) {
    this.flightOffers = flightOffers;
  }
}
