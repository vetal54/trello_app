create TABLE attachment_file
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(256) NOT NULL,
    type          VARCHAR(256) NOT NULL,
    file          bytea        NOT NULL,
    attachment_id UUID,
    CONSTRAINT fk_attachment_file_to_attachment FOREIGN KEY (attachment_id) REFERENCES "attachment" (id) ON DELETE CASCADE
)