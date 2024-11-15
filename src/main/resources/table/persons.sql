--liquibase formatted sql

--changeset rusilee:persons.create runInTransaction:false runOnChange:false
create table if not exists  persons
(
    id         bigserial primary key,
    name       varchar(127) not null,
    birthdate  timestamp,
    weight     double precision,
    eye_color  varchar(63),
    hair_color varchar(63)  not null
);
