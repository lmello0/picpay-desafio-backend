create table if not exists users (
    id varchar(128) primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    cpf varchar(11) not null unique,
    email varchar(100) not null unique,
    password varchar(100) not null,
    active boolean
)