INSERT INTO authors (firstname,lastname)
VALUES ('Frank','Herbert'), ('Boris','Strugatsky'), ('Arcadiy','Strugatsky');

INSERT INTO genres (name)
VALUES ('Science fiction'), ('Fantasy'), ('Philosophy');

INSERT INTO books (name)
VALUES ('Dune'), ('Escape Attempt');

INSERT INTO books_authors (book_id, author_id)
VALUES (1, 1), (2, 2), (2, 3);

INSERT INTO books_genres (book_id, genre_id)
VALUES (1, 1), (1, 2), (2, 1), (2, 3);