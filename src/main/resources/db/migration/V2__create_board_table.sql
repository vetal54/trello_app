CREATE TABLE board(
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    workspace_id UUID CONSTRAINT board_details_id_fk REFERENCES "workspace" (id)
);