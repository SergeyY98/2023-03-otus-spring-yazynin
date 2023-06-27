CREATE TABLE GENRES(
    ID BIGSERIAL,
    NAME VARCHAR(255),
    PRIMARY KEY (ID)
);

CREATE TABLE AUTHORS(
    ID BIGSERIAL,
    FIRSTNAME VARCHAR(255),
    LASTNAME VARCHAR(255),
    PRIMARY KEY (ID)
);

CREATE TABLE BOOKS(
    ID BIGSERIAL,
    NAME VARCHAR(255),
    PRIMARY KEY (ID)
);

create table BOOKS_AUTHORS(
    BOOK_ID bigint references BOOKS(ID) on delete cascade,
    AUTHOR_ID bigint references AUTHORS(ID),
    primary key (BOOK_ID, AUTHOR_ID)
);

create table BOOKS_GENRES(
    BOOK_ID bigint references BOOKS(ID) on delete cascade,
    GENRE_ID bigint references GENRES(ID),
    primary key (BOOK_ID, GENRE_ID)
);