CREATE TABLE comment
(
    id          UUID PRIMARY KEY,
    context     VARCHAR(255) NOT NULL,
    create_by   VARCHAR(255) NOT NULL,
    update_by   VARCHAR(255),
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP,
    member_id   UUID,
    CONSTRAINT fk_member_to_comment FOREIGN KEY (member_id) REFERENCES "member" (id) ON DELETE CASCADE,
    card_id     UUID,
    CONSTRAINT fk_card_to_comment FOREIGN KEY (card_id) REFERENCES "card" (id) ON DELETE CASCADE
);