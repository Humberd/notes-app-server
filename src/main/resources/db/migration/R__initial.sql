drop table if exists Note_Comment;
drop table if exists Note;
drop table if exists Login_Password_Auth;
drop table if exists User_Auth;
drop table if exists "user";

create table "user"
(
    id         varchar(32)  not null primary key,
    name       varchar(255) not null,
    name_lc    varchar(255) not null unique generated always as ( lower(name) ) stored,
    created_at timestamp    not null default now(),
    updated_at timestamp    not null default now()
);

create trigger set_updated_at
    before update
    on "user"
    for each row
execute procedure trigger_set_timestamp();


------- User_Auth
create table User_Auth
(
    user_id    varchar(32) not null primary key references "user" (id) on delete cascade,
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
    user_auth_id  varchar(32)  not null primary key references User_Auth (user_id) on delete cascade,
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
    id             varchar(32) not null primary key,
    author_id      varchar(32) null references "user" (id) on delete set null,
    url            text        not null,
    title          text        not null,
    content        text        not null,
    is_starred     boolean     not null default FALSE,
    is_deleted     boolean     not null default FALSE,
    comments_count integer     not null default 0,
    created_at     timestamp   not null default now(),
    updated_at     timestamp   not null default now()
);

create trigger set_updated_at
    before update
    on Note
    for each row
execute procedure trigger_set_timestamp();


-------- NoteComment
create table Note_Comment
(
    id         varchar(32) not null primary key,
    author_id  varchar(32) null references "user" (id) on delete set null,
    note_id    varchar(32) not null references Note (id) on delete cascade,
    content    text        not null,
    created_at timestamp   not null default now(),
    updated_at timestamp   not null default now()
);

create trigger set_updated_at
    before update
    on Note_Comment
    for each row
execute procedure trigger_set_timestamp();

create trigger increment_comments_count
    after insert
    on Note_Comment
    for each ROW
execute procedure trigger_increment_comments_count();

create trigger decrement_comments_count
    after delete
    on Note_Comment
    for each ROW
execute procedure trigger_decrement_comments_count();

--
insert into "user"(id, name)
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

insert into Note_Comment(id, author_id, note_id, content)
values ('a', '1', 'xyz', 'test'),
       ('b', '2', 'xyz', 'this comment rocks');

update Login_Password_Auth
set email = 'FOO@admin.com'
where user_auth_id = '1';
