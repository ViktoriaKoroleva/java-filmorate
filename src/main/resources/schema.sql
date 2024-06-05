create table if not exists users
(
    id int not null primary key auto_increment,
    login varchar(255) not null,
    name varchar(255),
    email varchar(255) not null,
    birthday date,
    unique(email),
    unique(login)
);

create table if not exists genres
(
    id int not null primary key auto_increment,
    name varchar(255)
);

create table if not exists mpa
(
    id int not null primary key auto_increment,
    name varchar(255)
);

create table if not exists films (
    id int not null primary key auto_increment,
    name VARCHAR(255),
    description VARCHAR(255),
    release_date date,
    duration int,
    mpa_id int references MPA(id)
);

create table if not exists film_genres
(
    film_id int references films(id),
    genre_id int references genres(id),
    primary key (film_id, genre_id)
);

create table if not exists friends
(
    user_id int references users(id),
    friend_id int references users(id),
    primary key (user_id, friend_id)
);

create table if not exists likes
(
    user_id int references users(id),
    film_id int references films(id),
    primary key (user_id, film_id)
);