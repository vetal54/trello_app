CREATE TABLE workspace_to_member
(
    id           SERIAL PRIMARY KEY,
    workspace_id UUID,
    CONSTRAINT fk_workspace FOREIGN KEY (workspace_id) REFERENCES "workspace" (id) ON DELETE CASCADE,
    member_id    UUID,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES "member" (id) ON DELETE CASCADE
);