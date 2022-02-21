CREATE TABLE reminder
(
    id          UUID PRIMARY KEY,
    start_on    TIMESTAMP,
    end_on      TIMESTAMP,
    remind_on   TIMESTAMP,
    active      BOOLEAN,
    card_id     UUID,
    CONSTRAINT fk_card_to_reminder FOREIGN KEY (card_id) REFERENCES "card" (id) ON DELETE CASCADE
);