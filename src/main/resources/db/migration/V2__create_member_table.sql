CREATE TABLE member
(
    id          UUID PRIMARY KEY,
    role        VARCHAR(15)  NOT NULL,
    user_id     UUID,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user_table" (id)
);