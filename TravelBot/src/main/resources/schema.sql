create table IF NOT EXISTS flight_subscriptions(
    id bigserial,
    chat_id bigserial,
    currency_code varchar(255),
    units bigserial,
    token varchar(1023),
    offer_key_to_highlight varchar(255),
    primary key (id)
);

create table IF NOT EXISTS segments(
    id bigserial,
    arrival_city_name varchar(255),
    arrival_time varchar(255),
    departure_city_name varchar(255),
    departure_time varchar(255),
    primary key (id)
);

create table IF NOT EXISTS flight_subscriptions_segments(
    flight_subscription_id bigserial references flight_subscriptions(id) on delete cascade,
    segment_id bigserial references segments(id) on delete cascade,
    primary key (flight_subscription_id, segment_id)
);