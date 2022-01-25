package spd.trello.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spd.trello.domain.Board;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
public class BoardRepositoryImpl implements IRepository<Board> {

    private final JdbcTemplate jdbcTemplate;

    public BoardRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String CREATE = "INSERT INTO board (id, name, create_by, create_date ,workspace_id) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE board SET name = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM board WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM board";
    private final String DELETE_BY_ID = "DELETE FROM board WHERE id=?";

    @Override
    public void create(Board board) {
        jdbcTemplate.update(
                CREATE,
                board.getId(),
                board.getName(),
                board.getCreateBy(),
                Timestamp.valueOf(board.getCreateDate()),
                board.getWorkspaceId()
        );
    }

    @Override
    public void update(Board board) {
        jdbcTemplate.update(
                UPDATE,
                board.getName(),
                board.getId()
        );
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
