CREATE TABLE card_to_member
(
    id           SERIAL PRIMARY KEY,
    card_id UUID,
    CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES "card" (id) ON DELETE CASCADE,
    member_id    UUID,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES "member" (id) ON DELETE CASCADE
);