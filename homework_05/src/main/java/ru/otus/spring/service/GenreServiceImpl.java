package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

@Service
public class GenreServiceImpl implements GenreService {
  private final IOService ioService;

  private final GenreDao genreDao;

  @Autowired
  public GenreServiceImpl(IOService ioService, GenreDao genreDao) {
    this.ioService = ioService;
    this.genreDao = genreDao;
  }

  @Override
  public void findAll() {
    genreDao.findAll().stream()
        .map(g -> g.getId() + ") " + g.getName() + "\n")
        .forEach(ioService::outputString);
  }

  @Override
  public void findById(long id) {
    try {
      Genre g = genreDao.findById(id);
      ioService.outputString(g.getId() + ") " + g.getName());
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No genre with selected id found");
    }
  }

  @Override
  public void deleteById(long id) {
    try {
      genreDao.findById(id);
      genreDao.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No genre with selected id found");
    }
  }

  @Override
  public void insert(String name) {
    genreDao.insert(new Genre(name));
  }

  @Override
  public void update(long id, String name) {
    try {
      genreDao.findById(id);
      genreDao.update(new Genre(id, name));
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No genre with selected id found");
    }
  }

  @Override
  public void count() {
    ioService.outputString("Genre count: " + genreDao.count());
  }
}
