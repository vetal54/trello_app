package spd.trello.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class WorkspaceRepositoryImpl implements AbstractRepository<Workspace> {

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE = "INSERT INTO workspace (id, name, description, visibility) VALUES (?, ?, ?, ?)";
    private final String UPDATE = "UPDATE workspace SET name = ?, description = ?, visibility = ?, update_date = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM workspace WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM workspace";
    private final String DELETE_BY_ID = "DELETE FROM workspace WHERE id=?";

    @Override
    public Workspace save(Workspace workspace) {
        jdbcTemplate.update(
                CREATE,
                workspace.getId(),
                workspace.getName(),
                workspace.getDescription(),
                String.valueOf(workspace.getVisibility())
        );
        return getById(workspace.getId());
    }

    @Override
    public Workspace update(Workspace workspace) {
        jdbcTemplate.update(
                UPDATE,
                workspace.getName(),
                workspace.getDescription(),
                String.valueOf(workspace.getVisibility()),
                Timestamp.valueOf(LocalDateTime.now()),
                workspace.getId()
        );
        return getById(workspace.getId());
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
