CREATE TABLE comment
(
    id          UUID PRIMARY KEY,
    text        VARCHAR(255) NOT NULL,
    create_by   VARCHAR(255),
    update_by   VARCHAR(255),
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    card_id     UUID
        CONSTRAINT comment_details_id_fk REFERENCES "card" (id)
);