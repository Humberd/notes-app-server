drop table if exists Note;
drop table if exists Login_Password_Auth;
drop table if exists User_Auth;
drop table if exists "USER";

create table "USER"
(
    id         varchar(30)  not null primary key,
    name       varchar(255) not null,
    created_at timestamp    not null default current_timestamp,
    updated_at timestamp    not null default current_timestamp on update current_timestamp
);

create table User_Auth
(
    user_id    varchar(30) not null primary key references "USER" (id) on delete cascade,
    created_at timestamp   not null default current_timestamp,
    updated_at timestamp   not null default current_timestamp on update current_timestamp
);

create table Login_Password_Auth
(
    user_auth_id  varchar(30)  not null primary key references User_Auth (user_id) on delete cascade,
    email         varchar(255) not null,
    password_hash varchar(255) not null,
    created_at    timestamp    not null default current_timestamp,
    updated_at    timestamp    not null default current_timestamp on update current_timestamp
);

create table Note
(
    id         varchar(30)   not null unique,
    author_id  varchar(30)   not null references "USER" (id) on delete cascade,
    url        varchar(255)  not null,
    title      varchar(255)  not null,
    content    varchar(2048) not null,
    is_starred boolean       not null default FALSE,
    is_deleted boolean       not null default FALSE,
    created_at timestamp     not null default current_timestamp,
    updated_at timestamp     not null default current_timestamp on update current_timestamp,
    primary key (id, author_id)
);

insert into USER(id, name)
VALUES ('1', 'test'),
       ('2', 'foo');

insert into User_Auth(user_id)
values ('1');

insert into Login_Password_Auth(user_auth_id, email, password_hash)
values ('1', 'admin@admin.com', 'xyz');

insert into Note(id, author_id, url, title, content)
VALUES ('xyz', '1', '123', '123', '123'),
       ('xyz2', '2', '132', '23', '12');

