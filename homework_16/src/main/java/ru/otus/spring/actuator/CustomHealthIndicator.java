package ru.otus.spring.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {

  private final Random random = new Random();

  @Override
  public Health health() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = ((UserDetails)principal).getUsername();

    if (username.equals("admin")) {
      return Health.up()
          .withDetail("message", "Верный пользователь")
          .build();
    } else {
      return Health.status(Status.DOWN).withDetail("message", "Неизвестный пользователь").build();
    }
  }
}
