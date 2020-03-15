drop table if exists Note;
drop table if exists Login_Password_Auth;
drop table if exists User_Auth;
drop table if exists "USER";

create table "USER"
(
    id         varchar(30)  not null primary key,
    name       varchar(255) not null,
    created_at timestamp    not null default now(),
    updated_at timestamp    not null default now()
);

create trigger set_updated_at
    before update
    on "USER"
    for each row
execute procedure trigger_set_timestamp();




------- User_Auth
create table User_Auth
(
    user_id    varchar(30) not null primary key references "USER" (id) on delete cascade,
    created_at timestamp   not null default now(),
    updated_at timestamp   not null default now()
);

create trigger set_updated_at
    before update
    on User_Auth
    for each row
execute procedure trigger_set_timestamp();




------- Login_Password_Auth
create table Login_Password_Auth
(
    user_auth_id  varchar(30)  not null primary key references User_Auth (user_id) on delete cascade,
    email         varchar(255) not null,
    email_lc      varchar(255) not null unique generated always as ( lower(email) ) STORED,
    password_hash varchar(255) not null,
    created_at    timestamp    not null default now(),
    updated_at    timestamp    not null default now()
);

create trigger set_updated_at
    before update
    on Login_Password_Auth
    for each row
execute procedure trigger_set_timestamp();


------- Note
create table Note
(
    id         varchar(30) not null unique,
    author_id  varchar(30) not null references "USER" (id) on delete cascade,
    url        text        not null,
    title      text        not null,
    content    text        not null,
    is_starred boolean     not null default FALSE,
    is_deleted boolean     not null default FALSE,
    created_at timestamp   not null default now(),
    updated_at timestamp   not null default now(),
    primary key (id, author_id)
);

create trigger set_updated_at
    before update
    on Note
    for each row
execute procedure trigger_set_timestamp();
--
insert into "USER"(id, name)
VALUES ('1', 'test'),
       ('2', 'foo');

insert into User_Auth(user_id)
values ('1');
--
insert into Login_Password_Auth(user_auth_id, email, password_hash)
values ('1', 'Admin@admin.com', 'xyz');

insert into Note(id, author_id, url, title, content)
VALUES ('xyz', '1', '123', '123', '123'),
       ('xyz2', '2', '132', '23', '12');

