package ru.otus.spring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping("/")
  public String listPage(Model model) {
    List<Book> books = bookService.findAll();
    model.addAttribute("books", books);
    return "list";
  }

  @GetMapping("/save")
  public String saveBook(Model model) {
    BookDto book = new BookDto();
    model.addAttribute("book", book);
    return "edit";
  }

  @GetMapping("/edit")
  public String editBook(@RequestParam("id") long id, Model model) {
    Book book = bookService.findById(id);
    model.addAttribute("book", book);
    return "edit";
  }

  @PostMapping("/edit")
  public String saveEditedBook(@Valid @ModelAttribute("book") BookDto book,
                         BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "edit";
    }
    bookService.save(book.toDomainObject());
    return "redirect:/";
  }

  @PostMapping(value = "/edit", params = {"addAuthor"})
  public String addAuthor(@Valid @ModelAttribute("book") BookDto book,
                       BindingResult bindingResult, Model model) {
    book.getAuthors().add(new AuthorDto());
    return "edit";
  }

  @PostMapping(value = "/edit", params = {"addGenre"})
  public String addGenre(@Valid @ModelAttribute("book") BookDto book,
                          BindingResult bindingResult, Model model) {
    book.getGenres().add(new GenreDto());
    return "edit";
  }

  @PostMapping(value = "/edit", params = {"deleteAuthor"})
  public String deleteAuthor(@RequestParam("deleteAuthor") int index, @Valid @ModelAttribute("book") BookDto book,
                          BindingResult bindingResult, Model model) {
    book.getAuthors().remove(index);
    return "edit";
  }

  @PostMapping(value = "/edit", params = {"deleteGenre"})
  public String deleteGenre(@RequestParam("deleteGenre") int index, @Valid @ModelAttribute("book") BookDto book,
                             BindingResult bindingResult, Model model) {
    book.getGenres().remove(index);
    return "edit";
  }

  @DeleteMapping(value = "/delete", params = {"id"})
  public String deleteBook(@RequestParam("id") long id, Model model) {
    bookService.deleteById(id);
    return "list";
  }
}
