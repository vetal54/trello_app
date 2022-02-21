CREATE TABLE card_list
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    archived    BOOLEAN      NOT NULL,
    create_by   VARCHAR(255) NOT NULL,
    update_by   VARCHAR(255),
    create_date TIMESTAMP    NOT NULL,
    update_date TIMESTAMP,
    board_id    UUID,
    CONSTRAINT fk_board_to_card_list FOREIGN KEY (board_id) REFERENCES "board" (id) ON DELETE CASCADE
);