package ru.otus.spring.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dto.BookDto;
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

  @HystrixCommand(commandProperties= {
      @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="7000")
  })
  @Override
  public List<Book> findAll() {
    return bookRepository.findAll();
  }

  @HystrixCommand(commandProperties= {
      @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="7000")
  })
  @Override
  public Book findById(long id) {
    try {
      return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    } catch (NoSuchElementException e) {
      return new Book();
    }
  }

  @HystrixCommand(commandProperties= {
      @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="7000")
  })
  @Transactional(readOnly = false)
  @Override
  public void deleteById(long id) {
      bookRepository.deleteById(id);
  }

  @HystrixCommand(commandProperties= {
      @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="7000")
  })
  @Transactional(readOnly = false)
  @Override
  public void save(Book book) {
    bookRepository.save(book);
  }
}
