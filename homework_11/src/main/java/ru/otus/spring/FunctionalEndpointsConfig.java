package ru.otus.spring;

import io.micrometer.common.util.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repository.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class FunctionalEndpointsConfig {
  @Bean
  public RouterFunction<ServerResponse> composedRoutes(BookRepository repository) {
    return route()
        // эта функция должна стоять раньше findAll - порядок следования роутов - важен
        .GET("/api/books",
            queryParam("name", StringUtils::isNotEmpty),
            request -> request.queryParam("name")
                .map(name -> ok().body(repository.findAll(), Book.class))
                .orElse(badRequest().build())
        )
        // Обратите внимание на использование хэндлера
        .GET("/api/books", accept(APPLICATION_JSON), new BookHandler(repository)::list)
        // Обратите внимание на использование pathVariable
        .GET("/api/books/{id}", accept(APPLICATION_JSON),
            request -> repository.findById(Long.parseLong(request.pathVariable("id")))
                .flatMap(person -> ok().contentType(APPLICATION_JSON).body(fromValue(person)))
                .switchIfEmpty(notFound().build())
        ).build();
  }

  // Это пример хэндлера, который даже не бин
  static class BookHandler {

    private final BookRepository repository;

    BookHandler(BookRepository repository) {
      this.repository = repository;
    }

    Mono<ServerResponse> list(ServerRequest request) {
      // Обратите внимание на пример другого порядка создания response от Flux
      return ok().contentType(APPLICATION_JSON).body(repository.findAll(), Book.class);
    }
  }
}
