package ru.otus.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;

@Service
@Slf4j
public class EggServiceImpl implements EggService {

  @Override
  public Butterfly produce(Caterpillar caterpillar) {
    log.info("Cooking {}", caterpillar.getName());
    delay();
    log.info("Cooking {} done", caterpillar.getName());
    return new Butterfly(caterpillar.getName(), caterpillar.isFullyTransformed());
  }

  private static void delay() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
