package ru.otus.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomUtils;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MetamorphosisServiceImpl implements MetamorphosisService {
  private static final String[] SPECIES = {"Monarch", "Papilionidae", "Viceroy"};

  private final CocoonGateway cocoon;

  public MetamorphosisServiceImpl(CocoonGateway cocoon) {
    this.cocoon = cocoon;
  }

  @Override
  public void process() {
    ForkJoinPool pool = ForkJoinPool.commonPool();
    for (int i = 0; i < 10; i++) {
      delay();
      int num = i + 1;
      pool.execute(() -> {
        Collection<Caterpillar> items = generateCaterpillars();
        log.info("{}, New catterpillar: {}", num,
            items.stream().map(Caterpillar::getName)
                .collect(Collectors.joining(",")));
        Collection<Butterfly> food = cocoon.process(items);
        log.info("{}, Ready butterfly: {}", num, food.stream()
            .map(Butterfly::getName)
            .collect(Collectors.joining(",")));
      });
    }
  }

  private static Caterpillar generateCaterpillar() {
    return new Caterpillar(SPECIES[RandomUtils.nextInt(0, SPECIES.length)], RandomUtils.nextBoolean());
  }

  private static Collection<Caterpillar> generateCaterpillars() {
    List<Caterpillar> items = new ArrayList<>();
    for (int i = 0; i < RandomUtils.nextInt(1, 5); ++i) {
      items.add(generateCaterpillar());
    }
    return items;
  }

  private void delay() {
    try {
      Thread.sleep(7000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }
}

