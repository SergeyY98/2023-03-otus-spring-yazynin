package ru.otus.spring.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.otus.spring.repository.BookRepository;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BookController.class)
public class BookControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @MockBean
  private BookRepository bookRepository;

  @Test
  void testRoute() {
    webTestClient.get()
        .uri("/api/books")
        .exchange()
        .expectStatus()
        .isOk();
  }
}
