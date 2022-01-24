package spd.trello.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spd.trello.domain.CheckList;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Component
public class CheckListRepositoryImpl implements IRepository<CheckList> {

    private final JdbcTemplate jdbcTemplate;

    public CheckListRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String CREATE = "INSERT INTO check_list (id, name, create_by, create_date) VALUES (?, ?, ?, ?)";
    private final String UPDATE = "UPDATE check_list SET name = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM check_list WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM check_list";
    private final String DELETE_BY_ID = "DELETE FROM check_list WHERE id=?";

    @Override
    public void create(CheckList checkList) {
        jdbcTemplate.update(
                CREATE,
                checkList.getId(),
                checkList.getName(),
                checkList.getCreateBy(),
                Timestamp.valueOf(checkList.getCreateDate())
        );
    }

    @Override
    public void update(CheckList checkList) {
        jdbcTemplate.update(
                UPDATE,
                checkList.getName(),
                checkList.getId()
        );
    }

    @Override
    public CheckList getById(UUID id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, this::map, id);
    }

    @Override
    public List<CheckList> getAll() {
        return jdbcTemplate.query(FIND_ALL, this::map);
    }

    @Override
    public boolean delete(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
    }

    private CheckList map(ResultSet rs, int rowNum) throws SQLException {
        CheckList checkList = new CheckList();
        checkList.setId(UUID.fromString(rs.getString("id")));
        checkList.setName(rs.getString("name"));
        return checkList;
    }
}
