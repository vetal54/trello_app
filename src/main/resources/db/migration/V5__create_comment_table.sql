CREATE TABLE comment (
    id UUID PRIMARY KEY,
    text VARCHAR (255) NOT NULL,
    card_id UUID CONSTRAINT comment_details_id_fk REFERENCES "card" (id)
);