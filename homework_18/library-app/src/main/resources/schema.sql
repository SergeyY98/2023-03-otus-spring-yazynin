create table IF NOT EXISTS genres(
    id BIGINT auto_increment NOT NULL,
    name VARCHAR(255),
    primary key (id)
);

create table IF NOT EXISTS authors(
    id BIGINT auto_increment NOT NULL,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    primary key (id)
);

create table IF NOT EXISTS books(
    id BIGINT auto_increment NOT NULL,
    name VARCHAR(255),
    primary key (id)
);

create table IF NOT EXISTS comments(
    id BIGINT auto_increment NOT NULL,
    commentator VARCHAR(255),
    text VARCHAR(255),
    book_id BIGINT references books(id) on delete cascade,
    primary key (id)
);

create table IF NOT EXISTS books_authors(
    book_id BIGINT references books(id) on delete cascade,
    author_id BIGINT references authors(id) on delete cascade,
    primary key (book_id, author_id)
);

create table IF NOT EXISTS books_genres(
    book_id BIGINT references books(id) on delete cascade,
    genre_id BIGINT references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);