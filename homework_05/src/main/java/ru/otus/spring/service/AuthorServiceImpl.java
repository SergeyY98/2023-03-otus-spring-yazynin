package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

@Service
public class AuthorServiceImpl implements AuthorService {
  private final IOService ioService;

  private final AuthorDao authorDao;

  @Autowired
  public AuthorServiceImpl(IOService ioService, AuthorDao authorDao) {
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
      Author a = authorDao.findById(id);
      ioService.outputString(a.getId() + ") " + a.getFirstname() + " " + a.getLastname());
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No author with selected id found");
    }
  }

  @Override
  public void deleteById(long id) {
    try {
      authorDao.findById(id);
      authorDao.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No author with selected id found");
    }
  }

  @Override
  public void insert(String firstname, String lastname) {
    authorDao.insert(new Author(firstname, lastname));
  }

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
