CREATE TABLE card_list
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    active      BOOLEAN      NOT NULL,
    create_by   VARCHAR(255),
    update_by   VARCHAR(255),
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    board_id    UUID
        CONSTRAINT cardList_details_id_fk REFERENCES "board" (id)
);