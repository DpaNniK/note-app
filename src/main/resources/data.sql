--Создаю дефолтного админа, т.к. именно он добавляет/удаляет пользователей (123123)
INSERT INTO users
VALUES (0, 'admin', 'adminov', 'defAdmin@yandex.ru',
        '$2a$12$t8Rx/R18Q6OIobWLWoC/u./UJS/1gyWDSeU5i8TBkGD26bddLycHm', 'ADMIN');