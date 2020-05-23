drop table if exists user_group_membership_tag_trigger;
drop table if exists user_group_membership;
drop table if exists group_post_user_state;
drop table if exists group_post;
drop table if exists "group";
drop table if exists resource_tag;
drop table if exists resource_upvote;
alter table if exists resource
    drop column latest_revision_id;
drop table if exists resource_revision;
drop table if exists resource;
drop type if exists resource_type;
drop type if exists resource_change_kind;


drop table if exists Note_Workspace;
drop table if exists Workspace;
drop table if exists Note_Tag;
drop table if exists Tag;
drop table if exists Note_User_Vote;
drop table if exists Note_Comment;
drop table if exists Note;
drop table if exists User_Password_Credentials;
drop table if exists Auth_Password_Credentials;
drop table if exists Auth_Google_Provider;
drop table if exists Auth_Github_Provider;
drop table if exists "user";

create table "user"
(
    id         varchar(32)  not null primary key,
    name       varchar(255) not null,
    name_lc    varchar(255) not null unique generated always as ( lower(name) ) stored,
    email      varchar(255) not null,
    email_lc   varchar(255) not null unique generated always as ( lower(email) ) stored,
    created_at timestamp    not null default now(),
    updated_at timestamp    not null default now()
);

create trigger set_updated_at
    before update
    on "user"
    for each row
execute procedure trigger_set_timestamp();

------- Auth Password Credentials
create table Auth_Password_Credentials
(
    user_id       varchar(32)  not null primary key references "user" (id) on delete cascade,
    password_hash varchar(255) not null,
    created_at    timestamp    not null default now(),
    updated_at    timestamp    not null default now()
);

create trigger set_updated_at
    before update
    on Auth_Password_Credentials
    for each row
execute procedure trigger_set_timestamp();

------- Auth Google Provider
create table Auth_Google_Provider
(
    user_id       varchar(32)  not null primary key references "user" (id) on delete cascade,
    id            varchar(32)  not null unique,
    name          varchar(255) not null,
    email         varchar(255) not null,
    picture       varchar(255) not null,
    refresh_token varchar(255) not null,
    created_at    timestamp    not null default now(),
    updated_at    timestamp    not null default now()
);

create trigger set_updated_at
    before update
    on Auth_Google_Provider
    for each row
execute procedure trigger_set_timestamp();

------- Auth Github Provider
create table Auth_Github_Provider
(
    user_id      varchar(32)  not null primary key references "user" (id) on delete cascade,
    id           varchar(32)  not null unique,
    login        varchar(255) not null,
    name         varchar(255) not null,
    email        varchar(255) not null,
    avatar_url   varchar(255) not null,
    access_token varchar(255) not null,
    created_at   timestamp    not null default now(),
    updated_at   timestamp    not null default now()
);

create trigger set_updated_at
    before update
    on Auth_Github_Provider
    for each row
execute procedure trigger_set_timestamp();

-------- Tag
create table Tag
(
    id               varchar(32) not null primary key,
    user_id          varchar(32) not null references "user" (id) on delete cascade,
    name             text        not null,
    name_lc          text        not null generated always as ( lower(name) ) stored,
    background_color text        null,
    notes_count      integer     not null default 0,
    created_at       timestamp   not null default now(),
    updated_at       timestamp   not null default now(),
    unique (user_id, name_lc)
);

---- resource-related tables
create type resource_type as enum ('link', 'note');
create type resource_change_kind as enum ('insert', 'update', 'delete');

create table resource
(
    id              varchar(32) not null primary key,
    author_id       varchar(32) null references "user" (id) on delete set null,
    revisions_count int         not null default 0,
    created_at      timestamp   not null default now()
);

create table resource_revision
(
    id          varchar(32)          not null primary key,
    resource_id varchar(32)          not null references resource (id) on delete cascade,
    change_kind resource_change_kind not null,
    type        resource_type        not null,
    payload     jsonb                not null,
    created_at  timestamp            not null default now()
);

alter table resource
    add column latest_revision_id varchar(32) references resource_revision (id);

create table resource_upvote
(
    resource_id varchar(32) not null references resource (id) on delete cascade,
    user_id     varchar(32) not null references "user" (id) on delete cascade,
    primary key (resource_id, user_id)
);

create table resource_tag
(
    resource_id varchar(32) not null references resource (id) on delete cascade,
    tag_id      varchar(32) not null references tag (id) on delete cascade,
    primary key (resource_id, tag_id)
);

create table "group"
(
    id       varchar(32) not null primary key,
    name     text        not null,
    icon_url text        not null
);

create table group_post
(
    id          varchar(32) not null primary key,
    group_id    varchar(32) not null references "group" (id) on delete cascade,
    resource_id varchar(32) not null references resource (id) on delete cascade,
    unique (group_id, resource_id)
);

create table group_post_user_state
(
    group_post_id        varchar(32) not null references group_post (id) on delete cascade,
    user_id              varchar(32) not null references "user" (id) on delete cascade,
    resource_revision_id varchar(32) not null references resource_revision on delete cascade,
    primary key (group_post_id, user_id)
);

create table user_group_membership
(
    user_id  varchar(32) not null references "user" (id) on delete cascade,
    group_id varchar(32) not null references "group" (id) on delete cascade,
    primary key (user_id, group_id)
);

create table user_group_membership_tag_trigger
(
    user_id  varchar(32) not null references "user" (id) on delete cascade,
    group_id varchar(32) not null references "group" (id) on delete cascade,
    tag_id   varchar(32) not null references tag (id) on delete cascade,
    primary key (user_id, group_id, tag_id)
);

insert into "user"(id, name, email)
values ('user-alice', 'alice', 'alice@gmail.com'),
       ('user-bob', 'bob', 'bob@gmail.com');

insert into Auth_Password_Credentials(user_id, password_hash)
values ('user-alice', '$2a$10$0T765q/oG9wvUDiYZ8EqGuIsA1wi4WrYWqRQ73Oj6tpeizyJdY0Pq'),
       ('user-bob', '$2a$10$0T765q/oG9wvUDiYZ8EqGuIsA1wi4WrYWqRQ73Oj6tpeizyJdY0Pq');

insert into "group"(id, name, icon_url)
values ('group-cool-bros', 'Cool bros', '');

insert into user_group_membership(user_id, group_id)
values ('user-alice', 'group-cool-bros'),
       ('user-bob', 'group-cool-bros');

insert into tag(id, user_id, name, background_color)
values ('tag-nice-designs', 'user-bob', 'nice designs', null);

insert into user_group_membership_tag_trigger(user_id, group_id, tag_id)
values ('user-bob', 'group-cool-bros', 'tag-nice-designs');

insert into resource(id, author_id)
values ('resource-google_com', 'user-bob');

insert into resource_revision(id, resource_id, change_kind, type, payload)
values ('resource-rev1-google_com', 'resource-google_com', 'insert', 'link', '{
  "url": "https://google.com"
}');

update resource
set latest_revision_id = 'resource-rev1-google_com'
where id = 'resource-google_com';

insert into resource_tag(resource_id, tag_id)
values ('resource-google_com', 'tag-nice-designs');

insert into group_post(id, group_id, resource_id)
values ('group-post-1', 'group-cool-bros', 'resource-google_com');

insert into group_post_user_state(group_post_id, user_id, resource_revision_id)
values ('group-post-1', 'user-alice', 'resource-rev1-google_com'),
       ('group-post-1', 'user-bob', 'resource-rev1-google_com');
