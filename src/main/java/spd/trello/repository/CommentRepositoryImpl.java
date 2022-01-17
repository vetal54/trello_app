package spd.trello.repository;

import spd.trello.domain.Comment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CommentRepositoryImpl implements IRepository<Comment> {

    private final DataSource ds;

    private final String CREATE = "INSERT INTO comment (id, text, card_id) VALUES (?, ?, ?)";
    private final String UPDATE = "UPDATE comment SET text = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM comment WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM comment";
    private final String DELETE_BY_ID = "DELETE FROM comment WHERE id=?";

    public CommentRepositoryImpl(DataSource dataSource) {
        ds = dataSource;
    }

    @Override
    public void create(Comment comment) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setObject(1, comment.getId());
            ps.setString(2, comment.getText());
            ps.setObject(3, comment.getCardId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Comment creation failed", e);
        }
    }

    @Override
    public void update(Comment comment) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, comment.getText());
            ps.setObject(2, comment.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("CheckList::update failed");
        }
    }

    @Override
    public Comment getById(UUID id) {
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Comment::findCommentById failed", e);
        }
        throw new IllegalStateException("Comment with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<Comment> getAll() {
        try (Connection con = ds.getConnection();
             ResultSet rs = con.createStatement().executeQuery(FIND_ALL)) {
            List<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                Comment comment = map(rs);
                comments.add(comment);
            }
            return comments;
        } catch (SQLException e) {
            System.out.println("Comments doesn't exists");
            return Collections.emptyList();
        }
    }

    @Override
    public boolean delete(UUID id) {
        if (getById(id) == null) {
            return false;
        }
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(DELETE_BY_ID)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException("Comment::findCommentById failed", e);
        }
    }

    private Comment map(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(UUID.fromString(rs.getString("id")));
        comment.setText(rs.getString("text"));
        comment.setId(UUID.fromString(rs.getString("card_id")));
        return comment;
    }
}
