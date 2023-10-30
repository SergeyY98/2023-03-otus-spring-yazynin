package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

  private final BookService bookService;

  @GetMapping
  public List<BookDto> getAllBooks() {
    return bookService.findAll().stream().map(BookDto::fromDomainObject)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public BookDto getBookById(@PathVariable("id") long id) {
    return BookDto.fromDomainObject(bookService.findById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteBook(@PathVariable("id") long id) {
    bookService.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(method = { RequestMethod.PUT, RequestMethod.POST })
  public BookDto saveBook(@RequestBody BookDto book) {
    bookService.save(book.toDomainObject());
    return book;
  }
}
