drop table if exists Note_Tag;
drop table if exists Tag;
drop table if exists Note_User_Vote;
drop table if exists Note_Comment;
drop table if exists Note;
drop table if exists User_Password_Credentials;
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

------- Credentials_Auth
create table User_Password_Credentials
(
    user_id       varchar(32)  not null primary key references "user" (id) on delete cascade,
    email         varchar(255) not null,
    email_lc      varchar(255) not null unique generated always as ( lower(email) ) STORED,
    password_hash varchar(255) not null,
    created_at    timestamp    not null default now(),
    updated_at    timestamp    not null default now()
);

create trigger set_updated_at
    before update
    on User_Password_Credentials
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
    comments_count integer     not null default 0,
    votes_score    integer     not null default 0 check ( votes_score >= 0 ),
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
    for each row
execute procedure trigger_increment_comments_count();

create trigger decrement_comments_count
    after delete
    on Note_Comment
    for each row
execute procedure trigger_decrement_comments_count();


-------- NoteUserVote
create table Note_User_Vote
(
    id         varchar(32) not null primary key,
    user_id    varchar(32) not null references "user" (id) on delete cascade,
    note_id    varchar(32) not null references Note (id) on delete cascade,
    is_upvote  boolean     not null,
    created_at timestamp   not null default now(),
    updated_at timestamp   not null default now()
);

create unique index unique_vote_per_note_per_user on Note_User_Vote (user_id, note_id);

create trigger set_updated_at
    before update
    on Note_User_Vote
    for each row
execute procedure trigger_set_timestamp();

create trigger update_comments_count
    after insert or update or delete
    on Note_User_Vote
    for each row
execute procedure trigger_update_comments_count();


-------- Tag
create table Tag
(
    id               varchar(32) not null primary key,
    user_id          varchar(32) not null references "user" (id) on delete cascade,
    name             text        not null,
    name_lc          text        not null unique generated always as ( lower(name) ) stored,
    background_color text        null,
    notes_count      integer     not null default 0,
    created_at       timestamp   not null default now(),
    updated_at       timestamp   not null default now()
);

create trigger set_updated_at
    before update
    on Tag
    for each row
execute procedure trigger_set_timestamp();


-------- NoteTag
create table Note_Tag
(
    note_id    varchar(32) not null references Note (id) on delete cascade,
    tag_id     varchar(32) not null references Tag (id) on delete cascade,
    created_at timestamp   not null default now(),
    updated_at timestamp   not null default now(),
    PRIMARY KEY (note_id, tag_id)
);

create trigger set_updated_at
    before update
    on Note_Tag
    for each row
execute procedure trigger_set_timestamp();

create trigger increment_notes_count
    after insert
    on Note_Tag
    for each row
execute procedure trigger_increment_notes_count();

create trigger decrement_notes_count
    after delete
    on Note_Tag
    for each row
execute procedure trigger_decrement_notes_count();

--
insert into "user"(id, name)
VALUES ('user-1', 'test'),
       ('user-2', 'foo');

--
insert into User_Password_Credentials(user_id, email, password_hash)
values ('user-1', 'Admin@admin.com', 'xyz');

insert into Note(id, author_id, url, title, content)
VALUES ('note-1', 'user-1', '123', '123', '123'),
       ('note-2', 'user-2', '132', '23', '12');

insert into Note_Comment(id, author_id, note_id, content)
values ('ncomment-1', 'user-1', 'note-1', 'test'),
       ('ncomment-2', 'user-2', 'note-1', 'this comment rocks');

insert into Note_User_Vote(id, user_id, note_id, is_upvote)
values ('nuvote-1', 'user-1', 'note-1', true);

insert into Tag(id, user_id, name)
values ('tag-1', 'user-1', 'java');

insert into Note_Tag(note_id, tag_id)
values ('note-1', 'tag-1');

