package ru.otus.spring.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDetails {
  @JsonProperty("status")
  private Boolean status;

  @JsonProperty("message")
  private String message;

  @JsonProperty("data")
  private Flight data;

  @JsonCreator
  public ResponseDetails(@JsonProperty("status") Boolean status,
                  @JsonProperty("message") String message,
                  @JsonProperty("data") Flight data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }
}
