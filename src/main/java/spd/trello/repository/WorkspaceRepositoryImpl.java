package spd.trello.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class WorkspaceRepositoryImpl implements IRepository<Workspace> {

    private final JdbcTemplate jdbcTemplate;

    public WorkspaceRepositoryImpl(DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    private final String CREATE = "INSERT INTO workspace (id, name, description, visibility) VALUES (?, ?, ?, ?)";
    private final String UPDATE = "UPDATE workspace SET name = ?, description = ?, visibility = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM workspace WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM workspace";
    private final String DELETE_BY_ID = "DELETE FROM workspace WHERE id=?";

    @Override
    public void create(Workspace workspace) {
        jdbcTemplate.update(
                CREATE,
                workspace.getId(),
                workspace.getName(),
                workspace.getDescription(),
                String.valueOf(workspace.getVisibility())
        );
    }

    @Override
    public void update(Workspace workspace) {
        jdbcTemplate.update(
                UPDATE,
                workspace.getName(),
                workspace.getDescription(),
                String.valueOf(workspace.getVisibility()),
                workspace.getId()
        );
    }

    @Override
    public Workspace getById(UUID id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, this::map, id);
    }

    @Override
    public List<Workspace> getAll() {
        return jdbcTemplate.query(FIND_ALL, this::map);
    }

    @Override
    public boolean delete(UUID id) {
        return jdbcTemplate.update(DELETE_BY_ID, id) > 0;
    }

    private Workspace map(ResultSet rs, int rowNum) throws SQLException {
        Workspace workspace = new Workspace();
        workspace.setId(UUID.fromString(rs.getString("id")));
        workspace.setName(rs.getString("name"));
        workspace.setDescription(rs.getString("description"));
        workspace.setVisibility(WorkspaceVisibility.valueOf(rs.getString("visibility")));
        return workspace;
    }
}
