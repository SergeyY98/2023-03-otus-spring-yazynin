package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.FlightSubscription;

import java.util.List;
import java.util.Optional;

public interface FlightSubscriptionRepository extends JpaRepository<FlightSubscription, Long> {
  List<FlightSubscription> findByChatId(long chatId);

  Optional<FlightSubscription> findByToken(String token);

  void deleteByToken(String token);
}
