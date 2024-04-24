# java-filmorate
https://dbdiagram.io/d/6627f43003593b6b61c5d61e
______
<img width="932" alt="SchemeDB" src="https://github.com/ViktoriaKoroleva/java-kanban/assets/148775573/5c24d7bb-52c1-4f7f-962f-93a5a91bbf4a">
______

Схема базы данных включает таблицы для фильмов, пользователей, лайков, жанров и друзей, а также таблицу для рейтингов фильмов. 
Вот некоторые примеры запросов для основных операций, которые  могут выполняться в приложении:

1. Получение информации о фильме:

SELECT *
FROM Film
WHERE id = 1;

2. Обновление информации о фильме:
   
UPDATE Film
SET description = 'Новое описание фильма'
WHERE id = 1;

3. Добавление лайка к фильму:

INSERT INTO likes (film_id, user_id)
VALUES (1, 1);
