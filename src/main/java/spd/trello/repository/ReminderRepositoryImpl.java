package spd.trello.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spd.trello.domain.Reminder;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class ReminderRepositoryImpl implements IRepository<Reminder> {

    private final JdbcTemplate jdbcTemplate;

    public ReminderRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String CREATE = "INSERT INTO reminder (id, startOn, endOn, remindOn, active, card_id) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE = "UPDATE reminder SET startOn = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM reminder WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM reminder";
    private final String DELETE_BY_ID = "DELETE FROM reminder WHERE id=?";

    @Override
    public void create(Reminder reminder) {
        jdbcTemplate.update(
                CREATE,
                reminder.getId(),
                Timestamp.valueOf(reminder.getStart()),
                Timestamp.valueOf(reminder.getEnd()),
                Timestamp.valueOf(reminder.getRemindOn()),
                reminder.getActive(),
                reminder.getCardId()
        );
    }

    @Override
    public void update(Reminder reminder) {
        jdbcTemplate.update(
                UPDATE,
                Timestamp.valueOf(reminder.getStart()),
                reminder.getId()
        );
    }

    @Override
    public Reminder getById(UUID id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, this::map, id);
    }

    @Override
    public List<Reminder> getAll() {
        return jdbcTemplate.query(FIND_ALL, this::map);
    }

    @Override
    public boolean delete(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
    }

    private Reminder map(ResultSet rs, int rowNum) throws SQLException {
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