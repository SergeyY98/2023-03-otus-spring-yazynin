package ru.otus.spring.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "segments")
public class Segment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String departureCityName;

  private String arrivalCityName;

  private String departureTime;

  private String arrivalTime;

  public Segment(String departureCityName, String arrivalCityName,
                                 String departureTime, String arrivalTime) {
    this.departureCityName = departureCityName;
    this.arrivalCityName = arrivalCityName;
    this.departureTime = departureTime;
    this.arrivalTime = arrivalTime;
  }
}
