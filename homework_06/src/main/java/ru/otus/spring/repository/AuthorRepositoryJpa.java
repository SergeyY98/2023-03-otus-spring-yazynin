package ru.otus.spring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

  @PersistenceContext
  private final EntityManager em;

  public AuthorRepositoryJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public long count() {
    return em.createQuery(
        "select count(a) from Author a"
        , Long.class).getSingleResult();
  }

  @Override
  public Author update(Author author) {
    if (author.getId() <= 0) {
      em.persist(author);
      return author;
    } else {
      return em.merge(author);
    }
  }

  @Override
  public Optional<Author> findById(long id) {
    return Optional.ofNullable(em.find(Author.class, id));
  }

  @Override
  public List<Author> findAll() {
    TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
    return query.getResultList();
  }

  @Override
  public void deleteById(long id) {
    em.remove(findById(id).get());
  }
}
