--liquibase formatted sql

--changeset rusilee:products.create runInTransaction:false runOnChange:false
create table if not exists products
(
    id              bigserial primary key,
    name            varchar(127)     not null,
    coordinate_x    double precision not null,
    coordinate_y    double precision not null,
    creation_date   timestamp      not null,
    price           double precision not null,
    part_number     varchar(63),
    unit_of_measure varchar(63)      not null,
    person_id       bigint references persons (id)
);