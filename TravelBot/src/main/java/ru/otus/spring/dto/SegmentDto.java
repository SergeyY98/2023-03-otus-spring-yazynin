package ru.otus.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.spring.domain.Segment;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SegmentDto {

  @JsonProperty("departureAirport")
  private Airport departureAirport;

  @JsonProperty("arrivalAirport")
  private Airport arrivalAirport;

  @JsonProperty("departureTime")
  private String departureTime;

  @JsonProperty("arrivalTime")
  private String arrivalTime;

  public Segment toDomainObject() {
    return new Segment(departureAirport.getCityName(), arrivalAirport.getCityName(), departureTime, arrivalTime);
  }
}
