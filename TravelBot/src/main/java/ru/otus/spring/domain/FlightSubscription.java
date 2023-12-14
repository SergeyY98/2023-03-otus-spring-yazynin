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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import ru.otus.spring.dto.PriceBreakdown;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flightSubscriptions")
public class FlightSubscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String token;

  private String offerKeyToHighlight;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable(name = "flight_subscriptions_segments", joinColumns = @JoinColumn(name = "flight_subscription_id"),
      inverseJoinColumns = @JoinColumn(name = "segment_id"))
  private List<Segment> segments;

  @Embedded
  private PriceBreakdown priceBreakdown;

  private long chatId;

  public FlightSubscription(String token, String offerKeyToHighlight, List<Segment> segments,
                            PriceBreakdown priceBreakdown, long chatId) {
    this.token = token;
    this.offerKeyToHighlight = offerKeyToHighlight;
    this.segments = segments;
    this.priceBreakdown = priceBreakdown;
    this.chatId = chatId;
  }
}
