CREATE TABLE checkable_item
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    checked       BOOLEAN      NOT NULL,
    check_list_id UUID,
    CONSTRAINT fk_check_list FOREIGN KEY (check_list_id) REFERENCES "check_list" (id) ON DELETE CASCADE
);