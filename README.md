# java-filmorate
https://dbdiagram.io/d/6627f43003593b6b61c5d61e

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
