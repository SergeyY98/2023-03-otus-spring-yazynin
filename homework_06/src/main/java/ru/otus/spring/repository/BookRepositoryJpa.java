package ru.otus.spring.repository;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

  @PersistenceContext
  private final EntityManager em;

  public BookRepositoryJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public long count() {
    return em.createQuery(
        "select count(b) from Book b"
        , Long.class).getSingleResult();
  }

  @Override
  public Book update(Book book) {
    if (book.getId() <= 0) {
      em.persist(book);
      return book;
    } else {
      return em.merge(book);
    }
  }


  @Override
  public Optional<Book> findById(long id) {
    return Optional.ofNullable(em.find(Book.class, id));
  }

  @Override
  public List<Book> findAll() {
    TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
    return query.getResultList();
  }

  @Override
  public void deleteById(long id) {
    em.remove(findById(id).orElseThrow(NoSuchElementException::new));
  }
}
