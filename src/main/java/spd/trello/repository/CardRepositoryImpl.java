package spd.trello.repository;

import spd.trello.domain.Card;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CardRepositoryImpl implements IRepository<Card> {

    private final DataSource ds;

    private final String CREATE = "INSERT INTO card (id, name, description, active, cardList_id) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE card SET name = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM card WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM card";
    private final String DELETE_BY_ID = "DELETE FROM card WHERE id=?";

    public CardRepositoryImpl(DataSource dataSource) {
        ds = dataSource;
    }

    @Override
    public void create(Card card) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setObject(1, card.getId());
            ps.setString(2, card.getName());
            ps.setString(3, card.getDescription());
            ps.setBoolean(4, card.getActive());
            ps.setObject(5, card.getCardList_id());
            ps.executeUpdate();
            System.out.println("A new card added to the database");
        } catch (SQLException e) {
            throw new IllegalStateException("Card creation failed", e);
        }
    }

    @Override
    public void update(Card card) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, card.getName());
            ps.setObject(2, card.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Card::update failed");
        }
    }

    @Override
    public Optional<Card> getById(UUID id) {
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(map(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Card::findCardById failed", e);
        }
        throw new IllegalStateException("Card with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<Card> getAll() {
        try (Connection con = ds.getConnection();
             ResultSet rs = con.createStatement().executeQuery(FIND_ALL)) {
            List<Card> cards = new ArrayList<>();
            while (rs.next()) {
                Card card = map(rs);
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            System.out.println("Cards doesn't exists");
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
            throw new IllegalStateException("Card::findCardById failed", e);
        }
    }

    private Card map(ResultSet rs) throws SQLException {
        Card card = new Card();
        card.setId(UUID.fromString(rs.getString("id")));
        card.setName(rs.getString("name"));
        card.setDescription(rs.getString("description"));
        card.setActive(rs.getBoolean("active"));
        card.setCardList_id(UUID.fromString(rs.getString("cardList_id")));
        return card;
    }
}
