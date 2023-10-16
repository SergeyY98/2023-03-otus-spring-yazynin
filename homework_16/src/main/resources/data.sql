INSERT INTO authors (firstname,lastname)
VALUES ('firstname_01','lastname_01'), ('firstname_02','lastname_02'), ('firstname_03','lastname_03'),
       ('firstname_04','lastname_04'), ('firstname_05','lastname_05');

INSERT INTO genres (name)
VALUES ('genre_01'), ('genre_02'), ('genre_03'), ('genre_04'), ('genre_05');

INSERT INTO books (name)
VALUES ('book_01'), ('book_02'), ('book_03');

INSERT INTO comments (commentator, text, book_id)
VALUES ('author_01', 'text_01', 1), ('author_02', 'text_02', 1), ('author_03', 'text_03', 2),
       ('author_02', 'text_04', 2), ('author_05', 'text_05', 3), ('author_06', 'text_06', 3);

INSERT INTO books_authors (book_id, author_id)
VALUES (1, 1),   (1, 2),   (1, 3),
       (2, 2),   (2, 3),   (2, 4),
       (3, 3),   (3, 4),   (3, 5);

INSERT INTO books_genres (book_id, genre_id)
VALUES (1, 1),   (1, 2),   (1, 3),
       (2, 2),   (2, 3),   (2, 4),
       (3, 3),   (3, 4),   (3, 5);

INSERT INTO users (username, password)
VALUES('admin','$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');

INSERT INTO authorities (name) VALUES('ROLE_ADMIN');

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, 1, 'admin');

INSERT INTO acl_class (id, class) VALUES
(1, 'ru.otus.spring.domain.Book');

INSERT INTO acl_object_identity (object_id_class, object_id_identity, owner_sid, entries_inheriting) VALUES
(1, 1, 1, 1),
(1, 2, 1, 1),
(1, 3, 1, 1);

INSERT INTO acl_entry (acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1),
(1, 2, 1, 2, 1, 1, 1),
(1, 3, 1, 4, 1, 1, 1),
(1, 4, 1, 8, 1, 1, 1),
(1, 5, 1, 16, 1, 1, 1),
(2, 1, 1, 1, 1, 1, 1),
(2, 2, 1, 2, 1, 1, 1),
(2, 3, 1, 4, 1, 1, 1),
(2, 4, 1, 8, 1, 1, 1),
(2, 5, 1, 16, 1, 1, 1),
(3, 1, 1, 1, 1, 1, 1),
(3, 2, 1, 2, 1, 1, 1),
(3, 3, 1, 4, 1, 1, 1),
(3, 4, 1, 8, 1, 1, 1),
(3, 5, 1, 16, 1, 1, 1);