drop table if exists User;
drop table if exists User_Auth;

create table User_Auth
(
    id         varchar2(30) not null primary key,
    created_at timestamp    not null default current_timestamp,
    updated_at timestamp    not null default current_timestamp on update current_timestamp
);

create table User
(
    id         varchar2(30)  not null primary key,
    name       varchar2(255) not null,
    user_auth_id    varchar2(30) not null references User_Auth (id),
    created_at timestamp     not null default current_timestamp,
    updated_at timestamp     not null default current_timestamp on update current_timestamp
);

insert into User_Auth(id)
values ('1');

insert into User(id, name, user_auth_id)
VALUES ('1', 'test', '1');

-- delete from User where id='1';

--
-- insert into User
--     (name)
-- VALUES ('test')


