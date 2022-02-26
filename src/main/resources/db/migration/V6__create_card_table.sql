CREATE TABLE card
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    archived    BOOLEAN      NOT NULL,
    create_by   VARCHAR(255) NOT NULL,
    update_by   VARCHAR(255),
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP,
    cardlist_id UUID,
    CONSTRAINT fk_cardList FOREIGN KEY (cardlist_id) REFERENCES "card_list" (id) ON DELETE CASCADE
);