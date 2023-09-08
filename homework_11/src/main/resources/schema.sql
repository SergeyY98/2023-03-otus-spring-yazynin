CREATE TABLE GENRES(
    ID bigserial,
    NAME VARCHAR(255),
    primary key (ID)
);

CREATE TABLE AUTHORS(
    ID bigserial,
    FIRSTNAME VARCHAR(255),
    LASTNAME VARCHAR(255),
    primary key (ID)
);

CREATE TABLE BOOKS(
    ID bigserial,
    NAME VARCHAR(255),
    primary key (ID)
);

create table COMMENTS(
    ID bigserial,
    COMMENTATOR VARCHAR(255),
    TEXT VARCHAR(255),
    BOOK_ID bigserial references BOOKS(ID) on delete cascade,
    primary key (ID)
);

create table BOOKS_AUTHORS(
    BOOK_ID bigserial references BOOKS(ID) on delete cascade,
    AUTHOR_ID bigserial references AUTHORS(ID) on delete cascade,
    primary key (BOOK_ID, AUTHOR_ID)
);

create table BOOKS_GENRES(
    BOOK_ID bigserial references BOOKS(ID) on delete cascade,
    GENRE_ID bigserial references GENRES(ID) on delete cascade,
    primary key (BOOK_ID, GENRE_ID)
);