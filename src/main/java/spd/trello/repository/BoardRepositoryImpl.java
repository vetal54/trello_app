package spd.trello.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spd.trello.domain.Board;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements AbstractRepository<Board> {

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE = "INSERT INTO board (id, name, create_by, create_date ,workspace_id) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE board SET name = ?, update_date = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM board WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM board";
    private final String DELETE_BY_ID = "DELETE FROM board WHERE id=?";

    @Override
    public Board save(Board board) {
        jdbcTemplate.update(
                CREATE,
                board.getId(),
                board.getName(),
                board.getCreateBy(),
                Timestamp.valueOf(board.getCreateDate()),
                board.getWorkspaceId()
        );
        return getById(board.getId());
    }

    @Override
    public Board update(Board board) {
        jdbcTemplate.update(
                UPDATE,
                board.getName(),
                Timestamp.valueOf(LocalDateTime.now()),
                board.getId()
        );
        return getById(board.getId());
    }

    @Override
    public Board getById(UUID id) throws IllegalStateException {
        return jdbcTemplate.queryForObject(FIND_BY_ID, this::map, id);
    }

    @Override
    public List<Board> getAll() {
        return jdbcTemplate.query(FIND_ALL, this::map);
    }

    @Override
    public boolean delete(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
    }

    private Board map(ResultSet rs, int rowNum) throws SQLException {
        Board board = new Board();
        board.setId(UUID.fromString(rs.getString("id")));
        board.setName(rs.getString("name"));
        board.setWorkspaceId(UUID.fromString(rs.getString("workspace_id")));
        return board;
    }
}
