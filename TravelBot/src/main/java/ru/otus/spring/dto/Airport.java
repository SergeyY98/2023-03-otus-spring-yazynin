package ru.otus.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {

  @JsonProperty("type")
  private String type;

  @JsonProperty("code")
  private String code;

  @JsonProperty("city")
  private String city;

  @JsonProperty("cityName")
  private String cityName;

  @JsonProperty("country")
  private String country;

  @JsonProperty("countryName")
  private String countryName;

  @JsonProperty("name")
  private String name;
}
