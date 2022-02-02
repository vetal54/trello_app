CREATE TABLE card
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    active      BOOLEAN      NOT NULL,
    create_by   VARCHAR(255),
    update_by   VARCHAR(255),
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    cardList_id UUID
        CONSTRAINT card_details_id_fk REFERENCES "card_list" (id) ON DELETE CASCADE
);