package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.FlightSubscription;

import java.util.List;

public interface FlightSubscriptionRepository extends JpaRepository<FlightSubscription, Long> {
  List<FlightSubscription> findByChatId(long chatId);

  List<FlightSubscription> findByOfferKeyToHighlight(String offerKeyToHighlight);
}
