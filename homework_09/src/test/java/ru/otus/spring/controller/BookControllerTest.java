package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(BookController.class)
public class BookControllerTest {

  private static final List<Genre> EXISTING_GENRES = List.of(new Genre(1, "genre_01"),
      new Genre(2, "genre_02"), new Genre(3, "genre_03"));

  private static final List<Author> EXISTING_AUTHORS = List.of(new Author(1, "firstname_01","lastname_01"),
      new Author(2, "firstname_02","lastname_02"), new Author(3, "firstname_03","lastname_03"));

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ObjectMapper mapper;

  @MockBean
  private BookService service;

  @Test
  void shouldReturnCorrectBooksList() throws Exception {
    List<Book> books = List.of(new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES, List.of()),
        new Book(2, "book_02", EXISTING_AUTHORS, EXISTING_GENRES, List.of()));
    given(service.findAll()).willReturn(books);

    mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("list"))
        .andExpect(model().attribute("books", hasSize(2)));
  }

  @Test
  void shouldReturnCorrectBook() throws Exception {
    Book book = new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES, List.of());
    given(service.findById(1L)).willReturn(book);

    mvc.perform(get("/edit")
          .param("id", "1"))
        .andExpect(status().isOk())
        .andExpect(view().name("edit"))
        .andExpect(model().attributeExists("book"));
  }

  @Test
  void shouldEditCorrectBook() throws Exception {
    mvc.perform(post("/edit")
            .param("id", "1")
            .param("name", "book_01"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
    verify(service, times(1)).save(any(Book.class));
  }

  @Test
  public void shouldSaveCorrectBook() throws Exception {
    Book book = new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES, List.of());
    given(service.findById(1L)).willReturn(book);

    mvc.perform(post("/edit")
            .param("id", "0")
            .param("name", "book_11"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"));
    verify(service, times(1)).save(any(Book.class));
  }

  @Test
  void shouldDeleteCorrectBook() throws Exception {
    mvc.perform(get("/delete")
            .param("id", "1"))
        .andExpect(status().is3xxRedirection());
    verify(service, times(1)).deleteById(1L);
  }
}
