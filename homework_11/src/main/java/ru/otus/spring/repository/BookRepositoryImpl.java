package ru.otus.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.domain.Book;

@Component
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
  private static final String SELECT_QUERY = """
            SELECT b.id b_id, b.name b_name, a.id a_id, a.firstname firstname, a.lastname lastname
            FROM books b
            LEFT JOIN books_authors ba ON ba.book_id = b.id
            LEFT JOIN authors a ON a.id = ba.author_id
            """;
  //LEFT JOIN books_genres bg ON bg.book_id = b.id,
  //                g.id g_id, g.name g_name
  //LEFT JOIN genres g ON g.id = bg.genre_id

  private final AuthorRepository authorRepository;

  //private final GenreRepository genreRepository;

  private final DatabaseClient client;

  @Override
  public Flux<Book> findAll() {
    String query = String.format("%s ORDER BY b.id", SELECT_QUERY);

    return client.sql(query)
        .fetch()
        .all()
        .bufferUntilChanged(result -> result.get("b_id"))
        .flatMap(Book::fromRows);
  }

  @Override
  public Mono<Book> findById(long id) {
    String query = String.format("%s WHERE b.id = :id", SELECT_QUERY);

    return client.sql(query)
        .bind("id", id)
        .fetch()
        .all()
        .bufferUntilChanged(result -> result.get("b_id"))
        .flatMap(Book::fromRows)
        .singleOrEmpty();
  }

  @Override
  public Mono<Book> save(Book book) {
    return this.saveBook(book)
        .flatMap(this::saveAuthors)
        .flatMap(this::saveBookAuthors);
        //.flatMap(this::saveGenres)
        //.flatMap(this::saveBookGenres);
  }

  @Override
  public Mono<Book> update(Book book) {
    return this.updateBook(book)
        .flatMap(this::saveAuthors)
        .flatMap(this::deleteBookAuthors)
        .flatMap(this::saveBookAuthors);
        //.flatMap(this::saveGenres)
        //.flatMap(this::deleteBookGenres)
        //.flatMap(this::saveBookGenres);
  }

  public Mono<Book> saveBook(Book book) {
      return client.sql("INSERT INTO books(name) VALUES(:name)")
          .bind("name", book.getName())
          .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
          .fetch().first()
          .doOnNext(result -> book.setId(Long.parseLong(result.get("id").toString())))
          .thenReturn(book);
  }

  public Mono<Book> updateBook(Book book) {
    return this.client.sql("UPDATE books SET name = :name WHERE id = :id")
        .bind("name", book.getName())
        .bind("id", book.getId())
        .fetch().first()
        .thenReturn(book);
  }

  @Override
  public Mono<Void> delete(Book book) {
    return client.sql("DELETE FROM books WHERE id = :id")
        .bind("id", book.getId())
        .fetch().first()
        .then();
  }

  private Mono<Book> saveAuthors(Book book) {
    return Flux.fromIterable(book.getAuthors())
        .flatMap(authorRepository::save)
        .collectList()
        .doOnNext(book::setAuthors)
        .thenReturn(book);
  }

  /*private Mono<Book> saveGenres(Book book) {
    return Flux.fromIterable(book.getGenres())
        .flatMap(genreRepository::save)
        .collectList()
        .doOnNext(book::setGenres)
        .thenReturn(book);
  }*/

  private Mono<Book> saveBookAuthors(Book book) {
    String query = "INSERT INTO BOOKS_AUTHORS(book_id, author_id) VALUES (:book_id, :author_id)";

    return Flux.fromIterable(book.getAuthors())
        .flatMap(author -> client.sql(query)
            .bind("book_id", book.getId())
            .bind("author_id", author.getId())
            .fetch().rowsUpdated())
        .collectList()
        .thenReturn(book);
  }

  private Mono<Book> saveBookGenres(Book book) {
    String query = "INSERT INTO BOOKS_GENRES(book_id, genre_id) VALUES (:book_id, :genre_id)";

    return Flux.fromIterable(book.getGenres())
        .flatMap(genre -> client.sql(query)
            .bind("book_id", book.getId())
            .bind("genre_id", genre.getId())
            .fetch().rowsUpdated())
        .collectList()
        .thenReturn(book);
  }

  private Mono<Book> deleteBookAuthors(Book book) {
    String query = "DELETE FROM BOOKS_AUTHORS WHERE book_id = :id";

    return Mono.just(book)
        .flatMap(dep -> client.sql(query)
            .bind("id", book.getId())
            .fetch().rowsUpdated())
        .thenReturn(book);
  }

  private Mono<Book> deleteBookGenres(Book book) {
    String query = "DELETE FROM BOOKS_GENRES WHERE book_id = :id";

    return Mono.just(book)
        .flatMap(dep -> client.sql(query)
            .bind("id", book.getId())
            .fetch().rowsUpdated())
        .thenReturn(book);
  }
}
