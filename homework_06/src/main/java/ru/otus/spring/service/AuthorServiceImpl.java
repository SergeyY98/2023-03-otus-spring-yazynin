package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.domain.Author;

@Service
public class AuthorServiceImpl implements AuthorService {
  private final IOService ioService;

  private final AuthorRepository authorDao;

  @Autowired
  public AuthorServiceImpl(IOService ioService, AuthorRepository authorDao) {
    this.ioService = ioService;
    this.authorDao = authorDao;
  }

  @Override
  public void findAll() {
    authorDao.findAll().stream()
        .map(a -> a.getId() + ") " + a.getFirstname() + " " + a.getLastname() + "\n")
        .forEach(ioService::outputString);
  }

  @Override
  public void findById(long id) {
    try {
      Author a = authorDao.findById(id).get();
      ioService.outputString(a.getId() + ") " + a.getFirstname() + " " + a.getLastname());
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No author with selected id found");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
    try {
      authorDao.findById(id);
      authorDao.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No author with selected id found");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void insert(String firstname, String lastname) {
    authorDao.update(new Author(0, firstname, lastname));
  }

  @Transactional(readOnly = false)
  @Override
  public void update(long id, String firstname, String lastname) {
    try {
      authorDao.findById(id);
      authorDao.update(new Author(id, firstname, lastname));
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No book with selected id found");
    }
  }

  @Override
  public void count() {
    ioService.outputString("Author count: " + authorDao.count());
  }
}
