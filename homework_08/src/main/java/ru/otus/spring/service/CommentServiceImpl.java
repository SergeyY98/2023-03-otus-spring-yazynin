package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.CommentRepository;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
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
  public String findAll() {
    return commentRepository.findAll().stream()
        .map(c -> c.getId() + " " + c.getCommentator() + " " + c.getText() + " " + c.getBook().getName())
        .collect(Collectors.joining("\n"));
  }

  @Override
  public String findAllByBookId(String id) {
    return commentRepository.findAllByBookId(id).stream()
        .map(c -> c.getId() + " " + c.getCommentator() + " " + c.getText() + " " + c.getBook().getName())
        .collect(Collectors.joining("\n"));
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(String id) {
    try {
      commentRepository.deleteById(id);
    } catch (NoSuchElementException e) {
      ioService.outputString("No comment with selected id found");
    }
  }
}
