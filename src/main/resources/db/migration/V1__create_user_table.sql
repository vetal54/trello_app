CREATE TABLE user_table
(
    id        UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email     VARCHAR(64)  NOT NULL
);