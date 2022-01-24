package spd.trello.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spd.trello.domain.Comment;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
public class CommentRepositoryImpl implements IRepository<Comment> {

    private final JdbcTemplate jdbcTemplate;

    public CommentRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String CREATE = "INSERT INTO comment (id, text, create_by, create_date, card_id) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE comment SET text = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM comment WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM comment";
    private final String DELETE_BY_ID = "DELETE FROM comment WHERE id=?";

    @Override
    public void create(Comment comment) {
        jdbcTemplate.update(
                CREATE,
                comment.getId(),
                comment.getText(),
                comment.getCreateBy(),
                Timestamp.valueOf(comment.getCreateDate()),
                comment.getCardId()
        );
    }

    @Override
    public void update(Comment comment) {
        jdbcTemplate.update(
                UPDATE,
                comment.getText(),
                comment.getId()
        );
    }

    @Override
    public Comment getById(UUID id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, this::map, id);
    }

    @Override
    public List<Comment> getAll() {
        return jdbcTemplate.query(FIND_ALL, this::map);
    }

    @Override
    public boolean delete(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
    }

    private Comment map(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();
        comment.setId(UUID.fromString(rs.getString("id")));
        comment.setText(rs.getString("text"));
        comment.setId(UUID.fromString(rs.getString("card_id")));
        return comment;
    }
}
