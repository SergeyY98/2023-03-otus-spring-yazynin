package ru.otus.spring.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

  private final AuthorRepository authorRepository;

  @GetMapping
  public List<Author> getAllBooks() {
    return authorRepository.findAll();
  }

  @GetMapping("/{id}")
  public Author getBookById(@PathVariable("id") long id) {
    return authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {
    authorRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public Author saveBook(@RequestBody Author author) {
    return authorRepository.save(author);
  }

}
