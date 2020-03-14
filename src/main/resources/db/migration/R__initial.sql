drop table if exists User_Auth;
drop table if exists User;

create table User
(
    id         varchar2(30)  not null primary key,
    name       varchar2(255) not null,
    created_at timestamp     not null default current_timestamp,
    updated_at timestamp     not null default current_timestamp on update current_timestamp
);

create table User_Auth
(
    user_id    varchar2(30) not null primary key references User (id) on delete cascade,
    created_at timestamp    not null default current_timestamp,
    updated_at timestamp    not null default current_timestamp on update current_timestamp
);

insert into User(id, name)
VALUES ('1', 'test');

insert into User_Auth(user_id)
values ('1');

--
-- insert into User
--     (name)
-- VALUES ('test')


