--Создаю дефолтного админа, т.к. именно он добавляет/удаляет пользователей
INSERT INTO users
VALUES (0, 'admin', 'adminov', 'defAdmin@yandex.ru',
        '$2a$12$XzgagW9aG1hSrpQFWWAZWu0d6wy7zzS0CF0iKC57FV0jARmPMZ3FG', 'ADMIN');