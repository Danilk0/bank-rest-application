--liquibase formatted sql

--changeset dmoskalyuk:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    password VARCHAR(500) NOT NULL UNIQUE ,
    email VARCHAR(255) NOT NULL UNIQUE ,
    birth_date DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(255),
    role VARCHAR(255),
    block boolean default false
);

--changeset dmoskalyuk:2
CREATE TABLE IF NOT EXISTS card
(
    id BIGSERIAL PRIMARY KEY ,
    number VARCHAR(255),
    validity_period DATE,
    status VARCHAR(16),
    balance decimal not null check ( balance > 0 ) default 0,
    user_id INT REFERENCES users(id)
);

--changeset dmoskalyuk:3
CREATE TABLE IF NOT EXISTS tasks
(
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT REFERENCES users(id)
);