create table IF NOT EXISTS flight_subscriptions(
    id bigserial,
    token VARCHAR(255),
    primary key (id)
);

create table IF NOT EXISTS segments(
    id bigserial,
    arrival_city_name varchar(255),
    arrival_time varchar(255),
    departure_city_name varchar(255),
    departure_time varchar(255),
    book_id bigserial references flight_subscriptions(ID) on delete cascade,
    primary key (id)
);
