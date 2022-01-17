package spd.trello.repository;

import lombok.RequiredArgsConstructor;
import spd.trello.domain.Reminder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
public class ReminderRepositoryImpl implements IRepository<Reminder> {

    private final DataSource ds;

    private final String CREATE = "INSERT INTO reminder (id, startOn, endOn, remindOn, active, card_id) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE reminder SET startOn = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM reminder WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM reminder";
    private final String DELETE_BY_ID = "DELETE FROM reminder WHERE id=?";

    @Override
    public void create(Reminder reminder) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setObject(1, reminder.getId());
            ps.setTimestamp(2, Timestamp.valueOf(reminder.getStart()));
            ps.setTimestamp(3, Timestamp.valueOf(reminder.getEnd()));
            ps.setTimestamp(4, Timestamp.valueOf(reminder.getRemindOn()));
            ps.setBoolean(5, reminder.getActive());
            ps.setObject(6, reminder.getCardId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Reminder creation failed", e);
        }
    }

    @Override
    public void update(Reminder reminder) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setTimestamp(1, Timestamp.valueOf(reminder.getStart()));
            ps.setObject(2, reminder.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Reminder::update failed");
        }
    }

    @Override
    public Reminder getById(UUID id) {
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Reminder::findReminderById failed", e);
        }
        throw new IllegalStateException("Reminder with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<Reminder> getAll() {
        try (Connection con = ds.getConnection();
             ResultSet rs = con.createStatement().executeQuery(FIND_ALL)) {
            List<Reminder> reminders = new ArrayList<>();
            while (rs.next()) {
                Reminder reminder = map(rs);
                reminders.add(reminder);
            }
            return reminders;
        } catch (SQLException e) {
            System.out.println("Reminders doesn't exists");
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
            throw new IllegalStateException("Reminder::findReminderById failed", e);
        }
    }

    private Reminder map(ResultSet rs) throws SQLException {
        Reminder reminder = new Reminder();
        reminder.setId(UUID.fromString(rs.getString("id")));
        reminder.setStart(LocalDateTime.parse(rs.getTimestamp("startOn").toLocalDateTime().toString()));
        reminder.setEnd(LocalDateTime.parse(rs.getTimestamp("endOn").toLocalDateTime().toString()));
        reminder.setRemindOn(LocalDateTime.parse(rs.getTimestamp("remindOn").toLocalDateTime().toString()));
        reminder.setActive(rs.getBoolean("active"));
        reminder.setCardId(UUID.fromString(rs.getString("card_id")));
        return reminder;
    }
}
