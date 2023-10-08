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

create table IF NOT EXISTS users (
  id bigserial,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  primary key (id)
);

create table IF NOT EXISTS authorities (
  id bigserial,
  name VARCHAR(50) NOT NULL,
  primary key (id)
);

create table IF NOT EXISTS users_authorities(
    user_id bigserial references users(id) on delete cascade,
    authority_id bigserial references authorities(id) on delete cascade,
    primary key (user_id, authority_id)
);

create table acl_sid(
	id bigint not null auto_increment primary key,
	principal boolean not null,
	sid varchar_ignorecase(100) not null,
	constraint unique_uk_1 unique(sid,principal)
);

create table acl_class(
	id bigint not null auto_increment primary key,
	class varchar_ignorecase(100) not null,
	constraint unique_uk_2 unique(class)
);

create table acl_object_identity(
	id bigint not null auto_increment primary key,
	object_id_class bigint not null,
	object_id_identity bigint not null,
	parent_object bigint,
	owner_sid bigint,
	entries_inheriting boolean not null,
	constraint unique_uk_3 unique(object_id_class,object_id_identity)
);

create table acl_entry(
	id bigint not null auto_increment primary key,
	acl_object_identity bigint not null,
	ace_order int not null,
	sid bigint not null,
	mask integer not null,
	granting boolean not null,
	audit_success boolean not null,
	audit_failure boolean not null,
	constraint unique_uk_4 unique(acl_object_identity,ace_order)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);