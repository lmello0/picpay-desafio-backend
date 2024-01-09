create table if not exists users (
    id varchar(128) primary key,
    first_name varchar(50) not null,
    last_name varchar(50),
    document varchar(14) not null unique,
    email varchar(100) not null unique,
    password varchar(100) not null,
    type varchar(10),
    wallet integer,
    active boolean
)