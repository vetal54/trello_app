CREATE TABLE card_list(
    id UUID PRIMARY KEY,
    active BOOLEAN NOT NULL,
    board_id UUID CONSTRAINT cardList_details_id_fk REFERENCES "board" (id)
);