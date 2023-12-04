package ru.otus.spring.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import ru.otus.spring.dto.PriceBreakdown;
import ru.otus.spring.dto.SegmentDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flightSubscriptions")
public class FlightSubscription {

  @Id
  private long id;

  private String token;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Segment> segments;

  @Embedded
  private PriceBreakdown priceBreakdown;

  private long chatId;
}
