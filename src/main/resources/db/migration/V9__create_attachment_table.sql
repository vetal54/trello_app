CREATE TABLE attachment
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    link        VARCHAR(255) NOT NULL,
    create_by   VARCHAR(255) NOT NULL,
    update_by   VARCHAR(255),
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP,
    card_id     UUID,
    CONSTRAINT fk_card_to_attachment FOREIGN KEY (card_id) REFERENCES "card" (id),
    comment_id  UUID,
    CONSTRAINT fk_comment_to_attachment FOREIGN KEY (comment_id) REFERENCES "comment" (id)
);