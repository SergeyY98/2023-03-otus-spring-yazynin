package ru.otus.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class Airport {

  @JsonProperty("type")
  String type;

  @JsonProperty("code")
  String code;

  @JsonProperty("city")
  String city;

  @JsonProperty("cityName")
  String cityName;

  @JsonProperty("country")
  String country;

  @JsonProperty("countryName")
  String countryName;

  @JsonProperty("name")
  String name;
}
