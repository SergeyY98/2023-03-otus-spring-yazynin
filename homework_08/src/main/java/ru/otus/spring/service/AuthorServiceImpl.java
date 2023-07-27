package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorServiceImpl implements AuthorService {
  private final IOService ioService;

  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorServiceImpl(IOService ioService, AuthorRepository authorDao) {
    this.ioService = ioService;
    this.authorRepository = authorDao;
  }

  @Override
  public List<Author> findAll() {
    return authorRepository.findAll();
  }

  @Override
  public Author findById(String id) {
    return authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(String id) {
    try {
      authorRepository.deleteById(id);
    } catch (NoSuchElementException e) {
      ioService.outputString("No author with selected id found");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void save(String firstname, String lastname) {
    authorRepository.save(new Author(firstname, lastname));
  }

  @Override
  public void count() {
    ioService.outputString("Author count: " + authorRepository.count());
  }
}
