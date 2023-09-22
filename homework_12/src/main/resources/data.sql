INSERT INTO authors (firstname,lastname)
VALUES ('firstname_01','lastname_01'), ('firstname_02','lastname_02'), ('firstname_03','lastname_03'),
       ('firstname_04','lastname_04'), ('firstname_05','lastname_05'), ('firstname_06','lastname_06'),
       ('firstname_07','lastname_07'), ('firstname_08','lastname_08'), ('firstname_09','lastname_09'),
       ('firstname_10','lastname_10');

INSERT INTO genres (name)
VALUES ('genre_01'), ('genre_02'), ('genre_03'), ('genre_04'), ('genre_05'),
       ('genre_06'), ('genre_07'), ('genre_08'), ('genre_09'), ('genre_10');

INSERT INTO books (name)
VALUES ('book_01'), ('book_02'), ('book_03'), ('book_04'), ('book_05'),
       ('book_06'), ('book_07'), ('book_08'), ('book_09'), ('book_10');

INSERT INTO comments (commentator, text, book_id)
VALUES ('author_01', 'text_01', 1), ('author_02', 'text_02', 1), ('author_03', 'text_03', 2),
       ('author_02', 'text_04', 2), ('author_05', 'text_05', 3), ('author_06', 'text_06', 4),
       ('author_07', 'text_02', 5), ('author_08', 'text_08', 6), ('author_09', 'text_09', 7),
       ('author_10', 'text_10', 8), ('author_11', 'text_11', 9), ('author_12', 'text_12', 10);

INSERT INTO books_authors (book_id, author_id)
VALUES (1, 1),   (1, 2),   (1, 3),
       (2, 2),   (2, 4),   (2, 5),
       (3, 3),   (3, 6),   (3, 7),
       (4, 4),   (4, 8),   (4, 9),
       (5, 5),   (5, 10),  (5, 1),
       (6, 6),   (6, 2),   (6, 3),
       (7, 7),   (7, 4),   (7, 5),
       (8, 8),   (8, 6),   (8, 7),
       (9, 9),   (9, 8),   (9, 10),
       (10, 10), (10, 1),  (10, 2);

INSERT INTO books_genres (book_id, genre_id)
VALUES (1, 1),   (1, 2),   (1, 3),
       (2, 2),   (2, 4),   (2, 5),
       (3, 3),   (3, 6),   (3, 7),
       (4, 4),   (4, 8),   (4, 9),
       (5, 5),   (5, 10),  (5, 1),
       (6, 6),   (6, 2),   (6, 3),
       (7, 7),   (7, 4),   (7, 5),
       (8, 8),   (8, 6),   (8, 7),
       (9, 9),   (9, 8),   (9, 10),
       (10, 10), (10, 1),  (10, 2);

INSERT INTO users (username, password)
VALUES('admin','$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');

INSERT INTO authorities (name) VALUES('ROLE_ADMIN');