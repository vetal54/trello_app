CREATE TABLE comment (
    id UUID PRIMARY KEY,
    text VARCHAR (255) NOT NULL,
    create_by UUID,
    update_by UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    card_id UUID CONSTRAINT comment_details_id_fk REFERENCES "card" (id)
);