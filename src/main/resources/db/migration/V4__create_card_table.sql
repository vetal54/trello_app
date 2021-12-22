CREATE TABLE card (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL,
    cardList UUID NOT NULL,
    cardList_id UUID CONSTRAINT card_details_id_fk REFERENCES "card_list" (id)
);