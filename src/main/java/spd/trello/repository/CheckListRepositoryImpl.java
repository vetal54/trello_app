package spd.trello.repository;

import spd.trello.domain.Card;
import spd.trello.domain.CheckList;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CheckListRepositoryImpl implements IRepository<CheckList> {

    private final DataSource ds;

    private final String CREATE = "INSERT INTO check_list (id, name) VALUES (?, ?)";
    private final String UPDATE = "UPDATE check_list SET name = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM check_list WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM check_list";
    private final String DELETE_BY_ID = "DELETE FROM check_list WHERE id=?";

    public CheckListRepositoryImpl(DataSource dataSource) {
        ds = dataSource;
    }

    @Override
    public void create(CheckList checkList) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setObject(1, checkList.getId());
            ps.setString(2, checkList.getName());
            // ps.setObject(5, checkList.getCardList_id());
            ps.executeUpdate();
            System.out.println("A new checkList added to the database");
        } catch (SQLException e) {
            throw new IllegalStateException("CheckList creation failed", e);
        }
    }

    @Override
    public void update(CheckList checkList) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, checkList.getName());
            ps.setObject(2, checkList.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("CheckList::update failed");
        }
    }

    @Override
    public Optional<CheckList> getById(UUID id) {
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(map(resultSet));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("CheckList::findCheckListById failed", e);
        }
        throw new IllegalStateException("CheckList with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<CheckList> getAll() {
        try (Connection con = ds.getConnection();
             ResultSet rs = con.createStatement().executeQuery(FIND_ALL)) {
            List<CheckList> checkLists = new ArrayList<>();
            while (rs.next()) {
                CheckList checkList = map(rs);
                checkLists.add(checkList);
            }
            return checkLists;
        } catch (SQLException e) {
            System.out.println("CheckLists doesn't exists");
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
            throw new IllegalStateException("CheckList::findCheckListById failed", e);
        }
    }

    private CheckList map(ResultSet rs) throws SQLException {
        CheckList checkList = new CheckList();
        checkList.setId(UUID.fromString(rs.getString("id")));
        checkList.setName(rs.getString("name"));
        return checkList;
    }
}
