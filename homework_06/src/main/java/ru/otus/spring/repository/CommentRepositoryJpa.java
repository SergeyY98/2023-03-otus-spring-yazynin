package ru.otus.spring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpa implements CommentRepository {

  @PersistenceContext
  private final EntityManager em;

  public CommentRepositoryJpa(EntityManager em) {
    this.em = em;
  }

  @Override
  public Comment update(Comment comment) {
    if (comment.getId() <= 0) {
      em.persist(comment);
      return comment;
    } else {
      return em.merge(comment);
    }
  }

  @Override
  public Optional<Comment> findById(long id) {
    return Optional.ofNullable(em.find(Comment.class, id));
  }

  @Override
  public List<Comment> findAllByBookId(long book_id) {
    TypedQuery<Comment> query = em.createQuery(
        "select c from Comment c join fetch c.book b where b.id = :book_id", Comment.class);
    return query.setParameter("book_id", book_id).getResultList();
  }

  @Override
  public void deleteById(long id) {
    em.remove(em.find(Comment.class, id));
  }
}
