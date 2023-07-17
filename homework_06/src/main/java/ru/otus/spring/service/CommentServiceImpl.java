package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.domain.Comment;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentServiceImpl implements CommentService {
  private final IOService ioService;

  private final CommentRepository commentRepository;

  @Autowired
  public CommentServiceImpl(IOService ioService, CommentRepository commentRepository) {
    this.ioService = ioService;
    this.commentRepository = commentRepository;
  }

  @Override
  public List<Comment> findAllByBookId(long id) {
    return commentRepository.findAllByBookId(id);
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
    try {
      commentRepository.deleteById(id);
    } catch (NoSuchElementException e) {
      ioService.outputString("No comment with selected id found");
    }
  }
}
