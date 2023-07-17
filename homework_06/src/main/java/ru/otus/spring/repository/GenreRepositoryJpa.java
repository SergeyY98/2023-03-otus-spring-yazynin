package ru.otus.spring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

  @PersistenceContext
  private final EntityManager em;

  public GenreRepositoryJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public long count() {
    return em.createQuery(
        "select count(g) from Genre g"
        , Long.class).getSingleResult();
  }

  @Override
  public Genre update(Genre genre) {
    if (genre.getId() <= 0) {
      em.persist(genre);
      return genre;
    } else {
      return em.merge(genre);
    }
  }

  @Override
  public Optional<Genre> findById(long id) {
    return Optional.ofNullable(em.find(Genre.class, id));
  }

  @Override
  public List<Genre> findAll() {
    TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
    return query.getResultList();
  }


  @Override
  public void deleteById(long id) {
    em.remove(findById(id).orElseThrow(NoSuchElementException::new));
  }
}
