CREATE TABLE reminder
(
    id       UUID PRIMARY KEY,
    startOn  TIMESTAMP NOT NULL,
    endOn    TIMESTAMP NOT NULL,
    remindOn TIMESTAMP NOT NULL,
    active   BOOLEAN   NOT NULL,
    card_id  UUID
        CONSTRAINT reminder_details_id_fk REFERENCES "card" (id) ON DELETE CASCADE
);