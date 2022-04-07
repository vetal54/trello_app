CREATE TABLE label
(
    id      UUID PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    color   VARCHAR(255) NOT NULL,
    card_id UUID,
    CONSTRAINT fk_card_label FOREIGN KEY (card_id) REFERENCES "card" (id) ON DELETE CASCADE
);