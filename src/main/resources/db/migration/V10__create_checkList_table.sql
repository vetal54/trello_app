CREATE TABLE check_list
(
    id          UUID PRIMARY KEY,
    name        VARCHAR (255) NOT NULL,
    create_by   UUID,
    update_by   UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP
);