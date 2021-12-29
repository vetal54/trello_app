package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.DBActions;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

public class BoardDBActionsService implements DBActions<Board> {
    private final DataSource ds;
    private final String CREATE = "INSERT INTO board (id, name, workspace_id) VALUES (?, ?, ?)";
    private final String UPDATE = "UPDATE board SET name = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM board WHERE id=?";
    private final String DELETE_BY_ID = "DELETE FROM board WHERE id=?";

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new name of board");
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(UPDATE);
            ps.setString(1, scanner.nextLine());
            ps.setObject(2, board.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Board::update failed");
        }
    }

    @Override
    public Board get(UUID id) {
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Board::findBoardById failed", e);
        }
        throw new IllegalStateException("Board with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public boolean delete(UUID id) {
        if (get(id) == null) {
            return false;
        }
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_BY_ID)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException("Board::findBoardById failed", e);
        }
    }

    private Board map(ResultSet rs) throws SQLException {
        Board board = new Board();
        board.setId(UUID.fromString(rs.getString("id")));
        board.setName(rs.getString("name"));
        board.setWorkspace_id(UUID.fromString(rs.getString("workspace_id")));
        return board;
    }
}
