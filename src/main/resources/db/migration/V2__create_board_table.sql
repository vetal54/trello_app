CREATE TABLE board
(
    id           UUID PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    active       BOOLEAN      NOT NULL,
    description  VARCHAR(255),
    visibility   VARCHAR(10)  NOT NULL,
    create_by    VARCHAR(255),
    update_by    VARCHAR(255),
    create_date  TIMESTAMP,
    update_date  TIMESTAMP,
    workspace_id UUID
        CONSTRAINT board_details_id_fk REFERENCES "workspace" (id)
);