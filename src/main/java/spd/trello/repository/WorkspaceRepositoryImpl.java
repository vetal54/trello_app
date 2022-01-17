package spd.trello.repository;

import lombok.RequiredArgsConstructor;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
public class WorkspaceRepositoryImpl implements IRepository<Workspace> {

    private final DataSource ds;

    private final String CREATE = "INSERT INTO workspace (id, name, description, visibility) VALUES (?, ?, ?, ?)";
    private final String UPDATE = "UPDATE workspace SET name = ?, description = ?, visibility = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM workspace WHERE id=?";
    private final String FIND_ALL = "SELECT * FROM workspace";
    private final String DELETE_BY_ID = "DELETE FROM workspace WHERE id=?";

    @Override
    public void create(Workspace workspace) {
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE)) {
            ps.setObject(1, workspace.getId());
            ps.setString(2, workspace.getName());
            ps.setString(3, workspace.getDescription());
            ps.setString(4, String.valueOf(workspace.getVisibility()));
            ps.executeUpdate();
            System.out.println("A new workspace added to the database");
        } catch (SQLException e) {
            throw new IllegalStateException("Workspace creation failed", e);
        }
    }

    @Override
    public void update(Workspace workspace) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new name of workspace");
        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1, workspace.getName());
            ps.setString(2, workspace.getDescription());
            ps.setString(3, String.valueOf(workspace.getVisibility()));
            ps.setObject(4, workspace.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Workspace::update failed");
        }
    }

    @Override
    public Workspace getById(UUID id) {
        try (Connection con = ds.getConnection();
             PreparedStatement stmt = con.prepareStatement(FIND_BY_ID)) {
            stmt.setObject(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return map(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Workspace::findWorkspaceById failed", e);
        }
        throw new IllegalStateException("Workspace with ID: " + id.toString() + " doesn't exists");
    }

    @Override
    public List<Workspace> getAll() {
        try (Connection con = ds.getConnection();
             ResultSet rs = con.createStatement().executeQuery(FIND_ALL)) {
            List<Workspace> workspaces = new ArrayList<>();
            while (rs.next()) {
                Workspace workspace = map(rs);
                workspaces.add(workspace);
            }
            return workspaces;
        } catch (SQLException e) {
            System.out.println("Workspaces doesn't exists");
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
            throw new IllegalStateException("Workspace::findWorkspaceById failed", e);
        }
    }

    private Workspace map(ResultSet rs) throws SQLException {
        Workspace workspace = new Workspace();
        workspace.setId(UUID.fromString(rs.getString("id")));
        workspace.setName(rs.getString("name"));
        workspace.setDescription(rs.getString("description"));
        workspace.setVisibility(WorkspaceVisibility.valueOf(rs.getString("visibility")));
        return workspace;
    }
}
