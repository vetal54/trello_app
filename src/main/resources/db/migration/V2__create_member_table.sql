CREATE TABLE member
(
    id          UUID PRIMARY KEY,
    role        VARCHAR(15)  NOT NULL,
    update_by   VARCHAR(255),
    create_by   VARCHAR(255) NOT NULL,
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP,
    user_id     UUID,
    CONSTRAINT fk_user_to_user FOREIGN KEY (user_id) REFERENCES "user" (id)
);