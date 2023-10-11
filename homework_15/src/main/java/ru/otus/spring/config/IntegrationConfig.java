package ru.otus.spring.config;

import ru.otus.spring.domain.Caterpillar;
import ru.otus.spring.services.EggService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@Configuration
public class IntegrationConfig {
  @Bean
  public MessageChannelSpec<?, ?> routingChannel() {
    return MessageChannels.queue(10);
  }

  @Bean
  public MessageChannelSpec<?, ?> fullTransformationChannel() {
    return MessageChannels.queue(10);
  }

  @Bean
  public MessageChannelSpec<?, ?> partialTransformationChannel() {
    return MessageChannels.queue(10);
  }

  @Bean(name = PollerMetadata.DEFAULT_POLLER)
  public PollerSpec poller() {
    return Pollers.fixedRate(100).maxMessagesPerPoll(2);
  }

  @Bean
  public IntegrationFlow metamorphosisFlow(EggService eggService) {
    return IntegrationFlow.from(routingChannel())
        .split()
        .<Caterpillar, Boolean>route(
            Caterpillar::isFullyTransformed,
            mapping -> mapping
                .subFlowMapping(true, f -> f.handle(eggService, "produce"))
                .subFlowMapping(false, sf -> sf.gateway(f -> f.handle(eggService, "produce")))
        )
        .aggregate()
        .get();
  }
}
