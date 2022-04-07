CREATE TABLE board
(
    id           UUID PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    description  VARCHAR(255),
    visibility   VARCHAR(10)  NOT NULL,
    archived     BOOLEAN      NOT NULL,
    create_by    VARCHAR(255) NOT NULL,
    update_by    VARCHAR(255),
    create_date  TIMESTAMP    NOT NULL,
    update_date  TIMESTAMP,
    workspace_id UUID,
    CONSTRAINT fk_workspace_to_board FOREIGN KEY (workspace_id) REFERENCES "workspace" (id) ON DELETE CASCADE
);