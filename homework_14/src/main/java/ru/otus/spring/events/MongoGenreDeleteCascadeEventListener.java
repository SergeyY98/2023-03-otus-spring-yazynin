package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.GenreMongo;
import ru.otus.spring.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class MongoGenreDeleteCascadeEventListener extends AbstractMongoEventListener<GenreMongo> {

  private final BookRepository bookRepository;

  @Override
  public void onAfterDelete(AfterDeleteEvent<GenreMongo> event) {
    super.onAfterDelete(event);
    val source = event.getSource();
    val id = source.get("_id").toString();
    bookRepository.removeGenresArrayElementsById(id);
  }
}