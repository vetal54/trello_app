CREATE TABLE check_list
(
    id      UUID PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    card_id UUID,
    CONSTRAINT fk_card_to_checklist FOREIGN KEY (card_id) REFERENCES "card" (id) ON DELETE CASCADE
);