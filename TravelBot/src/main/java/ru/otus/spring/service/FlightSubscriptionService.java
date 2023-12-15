package ru.otus.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.FlightSubscription;
import ru.otus.spring.repository.FlightSubscriptionRepository;

import java.util.List;

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

  public void deleteUserSubscription(FlightSubscription flightSubscription) {
    subscriptionsRepository.delete(flightSubscription);
  }

  public List<FlightSubscription> getUsersSubscriptionByOfferKeyToHighlight(String offerKeyToHighlight) {
    return subscriptionsRepository.findByOfferKeyToHighlight(offerKeyToHighlight);
  }

  public List<FlightSubscription> getUsersSubscriptions(long chatId) {
    return subscriptionsRepository.findByChatId(chatId);
  }

}
