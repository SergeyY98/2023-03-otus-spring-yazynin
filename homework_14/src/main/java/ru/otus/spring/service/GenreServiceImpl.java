package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.domain.GenreMongo;

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
  public List<GenreMongo> findAll() {
    return genreRepository.findAll();
  }

  @Override
  public GenreMongo findById(String id) {
    return genreRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(String id) {
    genreRepository.deleteById(id);
  }

  @Transactional(readOnly = false)
  @Override
  public void save(String name) {
    genreRepository.save(new GenreMongo(name));
  }

  @Override
  public void count() {
    ioService.outputString("Genre count: " + genreRepository.count());
  }
}
