-- создаем все таблицы
CREATE TABLE IF NOT EXISTS users
(
    user_id   bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_name varchar(50),
    email     varchar(50) NOT null,
    login     varchar(50) NOT null,
    birthday  date        NOT null
);

CREATE TABLE IF NOT EXISTS friends
(
    request_friend_id  bigint REFERENCES public.users (user_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    response_friend_id bigint REFERENCES public.users (user_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT FRIENDS_PK PRIMARY KEY (request_friend_id, response_friend_id)
);

CREATE TABLE IF NOT EXISTS rating_mpa
(
    rating_mpa_id   bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    rating_mpa_name varchar(15)  NOT null,
    description     varchar(100) NOT null
);

CREATE TABLE IF NOT EXISTS films
(
    film_id       bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_name     varchar(100) NOT NULL,
    description   varchar(200),
    release_date  date,
    duration      integer,
    rate          integer,
    rating_mpa_id bigint REFERENCES public.rating_mpa (rating_mpa_id) ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS genres
(
    genre_id   bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_name varchar(100)
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id  bigint REFERENCES public.films (film_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    genre_id bigint REFERENCES public.genres (genre_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT FILM_GENRES_PK PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS likes
(
    user_id bigint REFERENCES public.users (user_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    film_id bigint REFERENCES public.films (film_id) ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT LIKES_PK PRIMARY KEY (user_id, film_id)
);
create unique index if not exists USER_EMAIL_UINDEX on USERS (email);
create unique index if not exists USER_LOGIN_UINDEX on USERS (login);

INSERT INTO genres(genre_name)
VALUES ('Комедия'),
       ('Драма'),
       ('Мультфильм'),
       ('Триллер'),
       ('Документальный'),
       ('Боевик');

INSERT INTO rating_mpa(rating_mpa_name, description)
VALUES ('G', 'у фильма нет возрастных ограничений'),
       ('PG', 'детям рекомендуется смотреть фильм с родителями'),
       ('PG-13', 'детям до 13 лет просмотр не желателен'),
       ('R', 'лицам до 17 лет просматривать фильм можно только в присутствии взрослого'),
       ('NC-17', 'лицам до 18 лет просмотр запрещён');