CREATE TABLE attachment
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    link        VARCHAR(255),
    size        DOUBLE PRECISION,
    create_by   VARCHAR(255) NOT NULL,
    update_by   VARCHAR(255),
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP,
    card_id     UUID,
    CONSTRAINT fk_card_to_attachment FOREIGN KEY (card_id) REFERENCES "card" (id) ON DELETE CASCADE
);