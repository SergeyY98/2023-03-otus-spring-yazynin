package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.repository.FlightSubscriptionRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FlightSubscriptionService {

  private final FlightSubscriptionRepository subscriptionsRepository;

  @Autowired
  public FlightSubscriptionService(FlightSubscriptionRepository subscriptionsRepository) {
    this.subscriptionsRepository = subscriptionsRepository;
  }

  public List<FlightSubscription> getAllSubscriptions() {
    return subscriptionsRepository.findAll();
  }

  public void saveUserSubscription(FlightSubscription flightSubscription) {
    subscriptionsRepository.save(flightSubscription);
  }

  public void deleteUserSubscription(String token) {
    subscriptionsRepository.deleteByToken(token);
  }

  public boolean hasTicketsSubscription(FlightSubscription userSubscription) {
    return !subscriptionsRepository.findByToken(userSubscription.getToken()).isEmpty();
  }

  public Optional<FlightSubscription> getUsersSubscriptionByToken(String token) {
    return subscriptionsRepository.findByToken(token);
  }

  public List<FlightSubscription> getUsersSubscriptions(long chatId) {
    return subscriptionsRepository.findByChatId(chatId);
  }

}
