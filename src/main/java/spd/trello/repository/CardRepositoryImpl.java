package spd.trello.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spd.trello.domain.Card;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
public class CardRepositoryImpl implements IRepository<Card> {

    private final JdbcTemplate jdbcTemplate;

    public CardRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String CREATE = "INSERT INTO card (id, name, description, active, create_by, create_date, cardList_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE card SET name = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM card WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM card";
    private final String DELETE_BY_ID = "DELETE FROM card WHERE id=?";

    @Override
    public void create(Card card) {
        jdbcTemplate.update(
                CREATE,
                card.getId(),
                card.getName(),
                card.getDescription(),
                card.getActive(),
                card.getCreateBy(),
                Timestamp.valueOf(card.getCreateDate()),
                card.getCardListId()
        );
    }

    @Override
    public void update(Card card) {
        jdbcTemplate.update(
                UPDATE,
                card.getName(),
                card.getId()
        );
    }

    @Override
    public Card getById(UUID id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, this::map, id);
    }

    @Override
    public List<Card> getAll() {
        return jdbcTemplate.query(FIND_ALL, this::map);
    }

    @Override
    public boolean delete(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
    }

    private Card map(ResultSet rs, int rowNum) throws SQLException {
        Card card = new Card();
        card.setId(UUID.fromString(rs.getString("id")));
        card.setName(rs.getString("name"));
        card.setDescription(rs.getString("description"));
        card.setActive(rs.getBoolean("active"));
        card.setCardListId(UUID.fromString(rs.getString("cardList_id")));
        return card;
    }
}
