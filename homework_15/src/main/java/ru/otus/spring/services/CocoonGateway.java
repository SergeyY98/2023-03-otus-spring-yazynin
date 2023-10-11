package ru.otus.spring.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Caterpillar;

import java.util.Collection;

@MessagingGateway
public interface CocoonGateway {

  @Gateway(requestChannel = "routingChannel", replyChannel = "fullTransformationChannel")
  Collection<Butterfly> process(Collection<Caterpillar> caterpillar);
}
