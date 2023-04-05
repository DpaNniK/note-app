### Приложение Notes

---

### Функционал приложения:

1) Пользователь:

- может создавать/удалять/обновлять заметки;
- может просматривать историю изменений заметки;
- может изменять данные своего профиля;

2) Администратор:

- имеет тот же самый функционал, что и пользователь
- может создавать/редактировать/удалять профиль пользователя
- может назначать пользователя администратором

---

### Доступ к swagger:

- http://localhost:8080/swagger-ui/index.html#/ (/NoteAPI)

---
Т.к. регистрация пользователя осуществляется при помощи администратора,
данные для авторизации нулевого администратора определены в бд:

- login - defAdmin@yandex.ru
- password - 123123

---

### Стек:

Java 11, Spring Boot, Hibernate, JPA, Spring Security, Swagger 3, Lombok,
PostgreSQL
