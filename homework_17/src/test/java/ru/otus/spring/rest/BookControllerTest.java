package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    List<Book> books = List.of(new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES),
        new Book(2, "book_02", EXISTING_AUTHORS, EXISTING_GENRES));
    given(service.findAll()).willReturn(books);

    List<BookDto> expectedResult = books.stream()
        .map(BookDto::fromDomainObject).collect(Collectors.toList());

    mvc.perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
  }

  @Test
  void shouldReturnCorrectBook() throws Exception {
    Book book = new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES);
    given(service.findById(1L)).willReturn(book);
    BookDto expectedResult = BookDto.fromDomainObject(book);

    mvc.perform(get("/api/books/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(expectedResult)));
  }

  @Test
  void shouldSaveCorrectBook() throws Exception {
    Book book = new Book(0, "book_11", EXISTING_AUTHORS, EXISTING_GENRES);
    String expectedResult = mapper.writeValueAsString(BookDto.fromDomainObject(book));
    mvc.perform(post("/api/books").contentType(APPLICATION_JSON)
            .content(expectedResult))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResult));
    verify(service, times(1)).save(any(Book.class));
  }

  @Test
  public void shouldEditCorrectBook() throws Exception {
    Book book = new Book(1, "book_01", EXISTING_AUTHORS, EXISTING_GENRES);
    String expectedResult = mapper.writeValueAsString(BookDto.fromDomainObject(book));
    mvc.perform(put("/api/books").contentType(APPLICATION_JSON)
            .content(expectedResult))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResult));
    verify(service, times(1)).save(any(Book.class));
  }

  @Test
  void shouldDeleteCorrectBook() throws Exception {
    mvc.perform(delete("/api/books/1"))
        .andExpect(status().isNoContent());
    verify(service, times(1)).deleteById(1L);
  }
}
