package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.NoSuchElementException;

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
  public List<Genre> findAll() {
    return genreRepository.findAll();
  }

  @Override
  public Genre findById(long id) {
    return genreRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
    try {
      genreRepository.deleteById(id);
    } catch (NoSuchElementException e) {
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
      genreRepository.update(new Genre(id, name));
    } catch (NoSuchElementException e) {
      ioService.outputString("No genre with selected id found");
    }
  }

  @Override
  public void count() {
    ioService.outputString("Genre count: " + genreRepository.count());
  }
}
