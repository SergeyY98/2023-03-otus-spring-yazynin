CREATE TABLE IF NOT EXISTS GENRES(
    ID bigserial,
    NAME VARCHAR(255),
    primary key (ID)
);

CREATE TABLE IF NOT EXISTS AUTHORS(
    ID bigserial,
    FIRSTNAME VARCHAR(255),
    LASTNAME VARCHAR(255),
    primary key (ID)
);

CREATE TABLE IF NOT EXISTS BOOKS(
    ID bigserial,
    NAME VARCHAR(255),
    primary key (ID)
);

create table IF NOT EXISTS COMMENTS(
    ID bigserial,
    COMMENTATOR VARCHAR(255),
    TEXT VARCHAR(255),
    BOOK_ID bigserial references BOOKS(ID) on delete cascade,
    primary key (ID)
);

create table IF NOT EXISTS BOOKS_AUTHORS(
    BOOK_ID bigserial references BOOKS(ID) on delete cascade,
    AUTHOR_ID bigserial references AUTHORS(ID) on delete cascade,
    primary key (BOOK_ID, AUTHOR_ID)
);

create table IF NOT EXISTS BOOKS_GENRES(
    BOOK_ID bigserial references BOOKS(ID) on delete cascade,
    GENRE_ID bigserial references GENRES(ID) on delete cascade,
    primary key (BOOK_ID, GENRE_ID)
);

CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_username
  on authorities (username,authority);