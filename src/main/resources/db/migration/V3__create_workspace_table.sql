CREATE TABLE workspace
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    visibility  VARCHAR(10)  NOT NULL,
    create_by   VARCHAR(255) NOT NULL,
    update_by   VARCHAR(255),
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP
);