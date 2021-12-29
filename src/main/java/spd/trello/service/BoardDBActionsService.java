package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.domain.Workspace;
import spd.trello.repository.DBActions;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class BoardDBActionsService implements DBActions<Board> {
    private final DataSource ds;
    private final String CREATE = "INSERT INTO board (id, name, workspace_id) VALUES (?, ?, ?)";

    public BoardDBActionsService(DataSource dataSource) {
        ds = dataSource;
    }

    @Override
    public void create(Board board) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(CREATE);
            ps.setObject(1, board.getId());
            ps.setString(2, board.getName());
            ps.setObject(3, board.getWorkspace_id());
            ps.executeUpdate();
            System.out.println("A new board added to the database");
        } catch (SQLException e) {
            throw new IllegalStateException("Board creation failed", e);
        }
    }

    @Override
    public void update(Board board) {
    }

    @Override
    public Board get(UUID id) {
        return null;
    }

    @Override
    public boolean delete(UUID id) {
        return false;
    }
}
