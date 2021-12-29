CREATE TABLE card_list(
    id UUID PRIMARY KEY,
    name VARCHAR (255) NOT NULL,
    active BOOLEAN NOT NULL,
    create_by UUID,
    update_by UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    board_id UUID CONSTRAINT cardList_details_id_fk REFERENCES "board" (id)
);