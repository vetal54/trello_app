package spd.trello.repository;

import spd.trello.domain.CardList;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CardListRepositoryImpl implements IRepository<CardList> {

    private final DataSource ds;

    private final String CREATE = "INSERT INTO card_list (id, name, active, board_id) VALUES (?, ?, ?, ?)";
    private final String UPDATE = "UPDATE card_list SET name = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM card_list WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM card_list";
    private final String DELETE_BY_ID = "DELETE FROM card_list WHERE id=?";

    public CardListRepositoryImpl(DataSource dataSource) {
        ds = dataSource;
    }

    @Override
    public void create(CardList cardList) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setObject(1, cardList.getId());
            ps.setString(2, cardList.getName());
            ps.setBoolean(3, cardList.getActive());
            ps.setObject(4, cardList.getBoardId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("CardList creation failed", e);
        }
    }

    @Override
    public void update(CardList cardList) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, cardList.getName());
            ps.setObject(2, cardList.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("CardList::update failed");
        }
    }

    @Override
    public CardList getById(UUID id) {
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("CardList::findCardListById failed", e);
        }
        throw new IllegalStateException("CardList with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<CardList> getAll() {
        try (Connection con = ds.getConnection();
             ResultSet rs = con.createStatement().executeQuery(FIND_ALL)) {
            List<CardList> cardLists = new ArrayList<>();
            while (rs.next()) {
                CardList cardList = map(rs);
                cardLists.add(cardList);
            }
            return cardLists;
        } catch (SQLException e) {
            System.out.println("CardLists doesn't exists");
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
            throw new IllegalStateException("CardList::findCardListById failed", e);
        }
    }

    private CardList map(ResultSet rs) throws SQLException {
        CardList cardList = new CardList();
        cardList.setId(UUID.fromString(rs.getString("id")));
        cardList.setName(rs.getString("name"));
        cardList.setActive(rs.getBoolean("active"));
        cardList.setBoardId(UUID.fromString(rs.getString("board_id")));
        return cardList;
    }
}
