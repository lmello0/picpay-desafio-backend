create table if not exists transactions (
    id varchar(128) not null,
    from_id varchar(128) not null,
    to_id varchar(128) not null,
    value integer,

    primary key(id),
    constraint fk_transactions_user_payer foreign key(from_id) references users(id),
    constraint fk_transactions_user_payee foreign key(to_id) references users(id)
);