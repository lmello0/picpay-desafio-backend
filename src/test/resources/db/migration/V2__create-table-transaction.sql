create table if not exists transactions (
    id varchar(128) primary key,
    from_id varchar(128) not null,
    to_id varchar(128) not null,
    "value" integer,
    created_at date,

    foreign key (from_id) references users(id),
    foreign key (to_id) references users(id)
);