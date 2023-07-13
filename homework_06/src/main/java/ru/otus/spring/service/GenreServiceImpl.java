package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.Genre;

@Service
public class GenreServiceImpl implements GenreService {
  private final IOService ioService;

  private final GenreRepository genreRepository;

  @Autowired
  public GenreServiceImpl(IOService ioService, GenreRepository genreRepository) {
    this.ioService = ioService;
    this.genreRepository = genreRepository;
  }

  @Override
  public void findAll() {
    genreRepository.findAll().stream()
        .map(g -> g.getId() + ") " + g.getName() + "\n")
        .forEach(ioService::outputString);
  }

  @Override
  public void findById(long id) {
    try {
      Genre g = genreRepository.findById(id).get();
      ioService.outputString(g.getId() + ") " + g.getName());
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No genre with selected id found");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
    try {
      genreRepository.findById(id);
      genreRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No genre with selected id found");
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void insert(String name) {
    genreRepository.update(new Genre(0, name));
  }

  @Transactional(readOnly = false)
  @Override
  public void update(long id, String name) {
    try {
      genreRepository.findById(id);
      genreRepository.update(new Genre(id, name));
    } catch (EmptyResultDataAccessException e) {
      ioService.outputString("No genre with selected id found");
    }
  }

  @Override
  public void count() {
    ioService.outputString("Genre count: " + genreRepository.count());
  }
}
