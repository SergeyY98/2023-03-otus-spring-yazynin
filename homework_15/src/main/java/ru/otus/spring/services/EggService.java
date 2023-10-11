package ru.otus.spring.services;

import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;

public interface EggService {
  Butterfly produce(Caterpillar caterpillar);
}
