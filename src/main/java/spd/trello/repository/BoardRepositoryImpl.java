package spd.trello.repository;

import lombok.RequiredArgsConstructor;
import spd.trello.domain.Board;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements IRepository<Board> {

    private final DataSource ds;

    private final String CREATE = "INSERT INTO board (id, name, workspace_id) VALUES (?, ?, ?)";
    private final String UPDATE = "UPDATE board SET name = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM board WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM board";
    private final String DELETE_BY_ID = "DELETE FROM board WHERE id=?";

    @Override
    public void create(Board board) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE)) {
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
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, board.getName());
            ps.setObject(2, board.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Board::update failed");
        }
    }

    @Override
    public Optional<Board> getById(UUID id) throws IllegalStateException {
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(map(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Board::findBoardById failed", e);
        }
        throw new IllegalStateException("Board with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<Board> getAll() {
        try (Connection con = ds.getConnection();
             ResultSet rs = con.createStatement().executeQuery(FIND_ALL)) {
            List<Board> boards = new ArrayList<>();
            while (rs.next()) {
                Board board = map(rs);
                boards.add(board);
            }
            return boards;
        } catch (SQLException e) {
            System.out.println("Workspaces doesn't exists");
            return Collections.emptyList();
        }
    }

    @Override
    public boolean delete(UUID id) {
        if (getById(id).isEmpty()) {
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
