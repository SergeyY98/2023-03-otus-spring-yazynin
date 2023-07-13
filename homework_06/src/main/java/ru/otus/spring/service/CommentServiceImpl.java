package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.CommentRepository;
import ru.otus.spring.domain.Comment;

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
  public void findById(long id) {
    try {
      Comment c = commentRepository.findById(id).get();
      ioService.outputString(c.getId() + ") " + c.getCommentator() + " " + c.getText());
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No comment with selected id found");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
    try {
      commentRepository.findById(id);
      commentRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No comment with selected id found");
    }
  }
}
