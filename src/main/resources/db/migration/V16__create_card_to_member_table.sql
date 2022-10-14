CREATE TABLE card_to_member
(
    id           SERIAL PRIMARY KEY,
    card_id UUID,
    CONSTRAINT fk_card_to_member FOREIGN KEY (card_id) REFERENCES "card" (id) ON DELETE CASCADE,
    member_id    UUID,
    CONSTRAINT fk_member_to_card FOREIGN KEY (member_id) REFERENCES "member" (id) ON DELETE CASCADE
);