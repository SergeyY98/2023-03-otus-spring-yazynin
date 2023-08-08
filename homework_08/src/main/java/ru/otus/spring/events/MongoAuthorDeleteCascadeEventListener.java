package ru.otus.spring.events;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class MongoAuthorDeleteCascadeEventListener extends AbstractMongoEventListener<Author> {

  private final BookRepository bookRepository;

  @Override
  public void onAfterDelete(AfterDeleteEvent<Author> event) {
    super.onAfterDelete(event);
    val source = event.getSource();
    val id = source.get("_id").toString();
    bookRepository.removeAuthorsArrayElementsById(id);
  }
}
