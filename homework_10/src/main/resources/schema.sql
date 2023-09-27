create table IF NOT EXISTS genres(
    id bigserial,
    name VARCHAR(255),
    primary key (id)
);

create table IF NOT EXISTS authors(
    id bigserial,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    primary key (id)
);

create table IF NOT EXISTS books(
    id bigserial,
    name VARCHAR(255),
    primary key (id)
);

create table IF NOT EXISTS comments(
    id bigserial,
    commentator VARCHAR(255),
    text VARCHAR(255),
    book_id bigserial references books(id) on delete cascade,
    primary key (id)
);

create table IF NOT EXISTS books_authors(
    book_id bigserial references books(id) on delete cascade,
    author_id bigserial references authors(id) on delete cascade,
    primary key (book_id, author_id)
);

create table IF NOT EXISTS books_genres(
    book_id bigserial references books(id) on delete cascade,
    genre_id bigserial references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);