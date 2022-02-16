CREATE TABLE "user"
(
    id          UUID PRIMARY KEY,
    firstName   VARCHAR(255) NOT NULL,
    lastName    VARCHAR(255) NOT NULL,
    email       VARCHAR(64)  NOT NULL,
    create_by   VARCHAR(255) NOT NULL,
    update_by   VARCHAR(255),
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP
);