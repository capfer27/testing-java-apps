create table if not exists books (
    id bigserial primary key,
    title varchar(255) not null,
    author varchar(255) not null,
    publication_year int not null
);