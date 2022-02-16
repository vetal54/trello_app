CREATE TABLE card_template
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    create_by   VARCHAR(255) NOT NULL,
    update_by   VARCHAR(255),
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP
);