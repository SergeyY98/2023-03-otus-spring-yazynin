package ru.otus.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerSecurityTest {

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

  @WithMockUser(
      username = "admin",
      authorities = {"ROLE_ADMIN"}
  )
  @ParameterizedTest
  @ValueSource(strings = {"/", "/edit", "/save"})
  public void testGetAuthenticatedOnAdmin(String input) throws Exception {
    Book book = new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES, List.of());
    given(service.findById(1L)).willReturn(book);
    mvc.perform(get(input)
            .param("id", "1")
            .with(csrf()))
        .andExpect(status().isOk());
  }

  @WithMockUser(
      username = "admin",
      authorities = {"ROLE_ADMIN"}
  )
  @ParameterizedTest
  @ValueSource(strings = {"addAuthor", "addGenre", "deleteAuthor", "deleteGenre"})
  public void testPostAuthenticatedOnAdmin(String param) throws Exception {
    Book book = new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES, List.of());
    given(service.findById(1L)).willReturn(book);

    mvc.perform(post("/edit")
            .param("name", "book_11")
            .param("id", "0")
            .param(param, "0")
            .with(csrf()))
        .andExpect(status().isOk());
  }

  @ParameterizedTest
  @ValueSource(strings = {"/", "/edit", "/save"})
  public void testGetNonAuthenticatedOnAdmin(String input) throws Exception {
    Book book = new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES, List.of());
    given(service.findById(1L)).willReturn(book);
    mvc.perform(get(input)
            .param("id", "1")
            .with(csrf()))
        .andExpect(status().isUnauthorized());
  }

  @ParameterizedTest
  @ValueSource(strings = {"addAuthor", "addGenre", "deleteAuthor", "deleteGenre"})
  public void testPostNonAuthenticatedOnAdmin(String param) throws Exception {
    Book book = new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES, List.of());
    given(service.findById(1L)).willReturn(book);

    mvc.perform(post("/edit")
            .param("name", "book_11")
            .param("id", "0")
            .param(param, "0")
            .with(csrf()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void testDeleteNonAuthenticatedOnAdmin() throws Exception {
    mvc.perform(delete("/delete")
            .param("id", "1")
            .with(csrf()))
        .andExpect(status().isUnauthorized());
    verify(service, times(0)).deleteById(1L);
  }

  @Test
  void testEditNonAuthenticatedOnAdmin() throws Exception {
    mvc.perform(post("/edit")
            .param("id", "1")
            .param("name", "book_01")
            .with(csrf()))
        .andExpect(status().isUnauthorized());
    verify(service, times(0)).save(any(Book.class));
  }
}
