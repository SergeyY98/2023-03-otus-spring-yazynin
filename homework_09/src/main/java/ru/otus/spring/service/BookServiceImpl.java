package ru.otus.spring.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.domain.Book;

import java.util.List;
import java.util.NoSuchElementException;

@Transactional(readOnly = true)
@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @Override
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @Override
  public Book findById(long id) {
    try {
      return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    } catch (NoSuchElementException e) {
      return new Book();
    }
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
      bookRepository.deleteById(id);
  }

  @Transactional(readOnly = false)
  @Override
  public void save(Book book) {
    bookRepository.save(book);
  }
}
