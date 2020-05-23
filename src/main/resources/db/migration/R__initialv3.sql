drop table if exists resource_upvote;
drop table if exists resource_revision;
drop table if exists resource;
drop type if exists resource_change_kind;
drop type if exists resource_type;

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

alter table resource add column latest_revision varchar(32) references resource_revision(id);

create table resource_upvote
(
    resource_id varchar(32) not null references resource (id) on delete cascade,
    user_id     varchar(32) not null references "user" (id) on delete cascade,
    primary key (resource_id, user_id)
);

create table "group"
(
    id       varchar(32) not null primary key,
    name     text        not null,
    icon_url text        not null
);

create table group_post
(
    group_id    varchar(32) not null references "group" (id) on delete cascade,
    resource_id varchar(32) not null references resource (id) on delete cascade,
    primary key (group_id, resource_id)
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

