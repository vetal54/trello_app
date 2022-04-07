CREATE TABLE card_to_label
(
    id        SERIAL PRIMARY KEY,
    card_id   UUID,
    CONSTRAINT fk_card_to_label FOREIGN KEY (card_id) REFERENCES "card" (id) ON DELETE CASCADE,
    label_id UUID,
    CONSTRAINT fk_label_to_card FOREIGN KEY (label_id) REFERENCES "label" (id) ON DELETE CASCADE
);