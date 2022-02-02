package spd.trello.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import spd.trello.domain.CardList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class CardListRepositoryImpl implements AbstractRepository<CardList> {

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE = "INSERT INTO card_list (id, name, active, create_by, create_date, board_id) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE card_list SET name = ?, update_date = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM card_list WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM card_list";
    private final String DELETE_BY_ID = "DELETE FROM card_list WHERE id=?";

    @Override
    public CardList save(CardList cardList) {
        jdbcTemplate.update(
                CREATE,
                cardList.getId(),
                cardList.getName(),
                cardList.getActive(),
                cardList.getCreateBy(),
                Timestamp.valueOf(cardList.getCreateDate()),
                cardList.getBoardId()
        );
        return getById(cardList.getId());
    }

    @Override
    public CardList update(CardList cardList) {
        jdbcTemplate.update(
                UPDATE,
                cardList.getName(),
                Timestamp.valueOf(LocalDateTime.now()),
                cardList.getId()
        );
        return getById(cardList.getId());
    }

    @Override
    public CardList getById(UUID id) {
         return jdbcTemplate.queryForObject(FIND_BY_ID, this::map, id);
    }

    @Override
    public List<CardList> getAll() {
        return jdbcTemplate.query(FIND_ALL, this::map);
    }

    @Override
    public boolean delete(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
    }

    private CardList map(ResultSet rs, int rowNum) throws SQLException {
        CardList cardList = new CardList();
        cardList.setId(UUID.fromString(rs.getString("id")));
        cardList.setName(rs.getString("name"));
        cardList.setActive(rs.getBoolean("active"));
        cardList.setBoardId(UUID.fromString(rs.getString("board_id")));
        return cardList;
    }
}
