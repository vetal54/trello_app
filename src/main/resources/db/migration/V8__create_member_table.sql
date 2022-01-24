CREATE TABLE member
(
    id      UUID PRIMARY KEY,
    role    VARCHAR(15) NOT NULL,
    user_id UUID
        CONSTRAINT member_details_id_fk REFERENCES "user" (id)
);