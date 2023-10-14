package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import ru.otus.spring.domain.BookMongo;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Override
  public void removeAuthorsArrayElementsById(String id) {
    val query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
    val update = new Update().pull("authors", query);
    mongoTemplate.updateMulti(new Query(), update, BookMongo.class);
  }

  @Override
  public void removeGenresArrayElementsById(String id) {
    val query = Query.query(Criteria.where("$id").is(new ObjectId(id)));
    val update = new Update().pull("genres", query);
    mongoTemplate.updateMulti(new Query(), update, BookMongo.class);
  }
}
