CREATE TABLE board
(
    id           UUID PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    create_by    VARCHAR(255),
    update_by    VARCHAR(255),
    create_date  TIMESTAMP,
    update_date  TIMESTAMP,
    workspace_id UUID REFERENCES "workspace" (id) ON DELETE CASCADE
);