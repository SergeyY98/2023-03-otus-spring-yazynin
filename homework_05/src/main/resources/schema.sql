CREATE TABLE GENRES(
    ID bigint AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255)
);

CREATE TABLE AUTHORS(
    ID bigint AUTO_INCREMENT PRIMARY KEY,
    FIRSTNAME VARCHAR(255),
    LASTNAME VARCHAR(255)
);

CREATE TABLE BOOKS(
    ID bigint AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255)
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