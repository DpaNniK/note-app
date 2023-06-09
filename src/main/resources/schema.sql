DROP TABLE IF EXISTS NOTES, HISTORY_NOTES, USERS;

CREATE TABLE USERS
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    surname  VARCHAR(255) NOT NULL,
    email    VARCHAR(512) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(100) NOT NULL,
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE NOTES
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    creator     BIGINT REFERENCES USERS (id),
    header      VARCHAR(255) NOT NULL,
    created     TIMESTAMP    NOT NULL,
    updated     TIMESTAMP    NOT NULL,
    description TEXT         NOT NULL
);

CREATE TABLE HISTORY_NOTES
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    note_id     INTEGER      NOT NULL,
    header      VARCHAR(255) NOT NULL,
    created     TIMESTAMP    NOT NULL,
    updated     TIMESTAMP    NOT NULL,
    description TEXT         NOT NULL
);

