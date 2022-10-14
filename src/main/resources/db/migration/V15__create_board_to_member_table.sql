CREATE TABLE board_to_member
(
    id           SERIAL PRIMARY KEY,
    board_id UUID,
    CONSTRAINT fk_board_to_member FOREIGN KEY (board_id) REFERENCES "board" (id) ON DELETE CASCADE,
    member_id    UUID,
    CONSTRAINT fk_member_to_board FOREIGN KEY (member_id) REFERENCES "member" (id) ON DELETE CASCADE
);