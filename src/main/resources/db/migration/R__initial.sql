drop table if exists Note_Workspace;
drop table if exists Workspace;
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
    id            varchar(32) not null primary key,
    author_id     varchar(32) null references "user" (id) on delete set null,
    url           text,
    url_lc        text generated always as ( lower(url) ) stored,
    title         text        not null,
    content       text,
    search_vector tsvector    not null,
    created_at    timestamp   not null default now(),
    updated_at    timestamp   not null default now()
);

create index search_vector_index on Note using gist (search_vector);
create index url_lc_index on Note (url_lc);

create trigger set_updated_at
    before update
    on Note
    for each row
execute procedure trigger_set_timestamp();

create trigger set_search_vector
    before insert or update
    on Note
    for each row
execute procedure trigger_set_search_vector();

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

--------- Workspace
create table Workspace
(
    id         varchar(32)  not null primary key,
    user_id    varchar(32)  not null references "user" (id) on delete cascade,
    name       text         not null,
    name_lc    varchar(255) not null unique generated always as ( lower(name) ) stored,
    created_at timestamp    not null default now(),
    updated_at timestamp    not null default now()
);

create trigger set_updated_at
    before update
    on Workspace
    for each row
execute procedure trigger_set_timestamp();

--------- NoteWorkspace
create table Note_Workspace
(
    note_id      varchar(32) not null references Note (id) on delete cascade,
    workspace_id varchar(32) not null references Workspace (id) on delete cascade,
    created_at   timestamp   not null default now(),
    updated_at   timestamp   not null default now(),
    PRIMARY KEY (note_id, workspace_id)
);

create trigger set_updated_at
    before update
    on Note_Workspace
    for each row
execute procedure trigger_set_timestamp();

--
insert into "user"(id, name)
VALUES ('user-1', 'test'),
       ('user-2', 'foo');

--
insert into User_Password_Credentials(user_id, email, password_hash)
values ('user-1', 'Admin@admin.com', '$2a$10$0T765q/oG9wvUDiYZ8EqGuIsA1wi4WrYWqRQ73Oj6tpeizyJdY0Pq');

insert into Note(id, author_id, url, title, content)
VALUES ('note-1', 'user-1', null, 'k3s config export', '```kubectl config view --raw >~/.kube/config```'),
       ('note-2', 'user-1', 'https://github.com/rancher/k3s/issues/703',
        'k3s dns not resolving when docker is installed', '```
sudo iptables -F
sudo update-alternatives --set iptables /usr/sbin/iptables-legacy
sudo reboot
```'),
       ('note-3', 'user-1', 'https://stackoverflow.com/a/54647323/4256929',
        'inject() must be called from an injection context', 'seems like an issue when using npm link when consuming the library.
Check out the following issue: https://github.com/angular/angular/issues/25813
Spoiler: use "projects.$name.architect.build.options.preserveSymlinks: true" in angular.json'),
       ('note-4', 'user-1', 'https://news.ycombinator.com/item?id=22749308', 'Hackernews jobs hiring', null),
       ('note-5', 'user-1', 'https://findwork.dev/?source=hn', 'Findwork', null),
       ('note-6', 'user-1', 'https://djqyo3vqv2.execute-api.us-west-1.amazonaws.com/latest/',
        'Hackernews jobs hiring searcher', null),
       ('note-7', 'user-1', 'https://hnjobs.emilburzo.com/', 'Hackernews jobs hiring search 2', null),
       ('note-8', 'user-1', 'https://kennytilton.github.io/whoishiring/', 'Hackernews jobs hiring search 3', null),
       ('note-9', 'user-1',
        'https://blog.soshace.com/the-ultimate-guide-to-drag-and-drop-image-uploading-with-pure-javascript/',
        'Drag and drop vanilla js', null);

insert into Tag(id, user_id, name, background_color)
values ('tag-1', 'user-1', 'kubernetes', '#ff00ff'),
       ('tag-2', 'user-1', 'k3s', '#45de78'),
       ('tag-3', 'user-1', 'docker', '#9ff37d'),
       ('tag-bug', 'user-1', 'bug', '#ff0000'),
       ('tag-angular', 'user-1', 'angular', null),
       ('tag-build', 'user-1', 'build', null),
       ('tag-job', 'user-1', 'Job', '#f032F1'),
       ('tag-js', 'user-1', 'js', null);

insert into Note_Tag(note_id, tag_id)
values ('note-1', 'tag-1'),
       ('note-1', 'tag-2'),
       ('note-2', 'tag-1'),
       ('note-2', 'tag-2'),
       ('note-2', 'tag-3'),
       ('note-3', 'tag-bug'),
       ('note-3', 'tag-angular'),
       ('note-3', 'tag-build'),
       ('note-4', 'tag-job'),
       ('note-5', 'tag-job'),
       ('note-6', 'tag-job'),
       ('note-7', 'tag-job'),
       ('note-8', 'tag-job'),
       ('note-9', 'tag-js');

insert into Workspace(id, user_id, name)
values ('workspace-work', 'user-1', 'Work'),
       ('workspace-job-searching', 'user-1', 'Job Searching'),
       ('workspace-private', 'user-1', 'Private'),
       ('workspace-work-2', 'user-2', 'My Work');

insert into Note_Workspace(note_id, workspace_id)
values ('note-1', 'workspace-work'),
       ('note-1', 'workspace-private'),
       ('note-2', 'workspace-private'),
       ('note-5', 'workspace-work'),
       ('note-5', 'workspace-job-searching'),
       ('note-5', 'workspace-private');


