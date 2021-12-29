CREATE TABLE board(
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    create_by UUID,
    update_by UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    workspace_id UUID CONSTRAINT board_details_id_fk REFERENCES "workspace" (id)
);