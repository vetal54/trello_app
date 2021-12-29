package spd.trello.service;

import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.DBActions;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

public class WorkspaceDBActionsService implements DBActions<Workspace> {

    private final DataSource ds;
    private final String CREATE = "INSERT INTO workspace (id, name, description, visibility) VALUES (?, ?, ?, ?)";
    private final String UPDATE = "UPDATE workspace SET name = ?, description = ?, visibility = ? WHERE id = ?";
    private final String FIND_BY_ID = "SELECT * FROM workspace WHERE id=?";
    private final String DELETE_BY_ID = "DELETE FROM workspace WHERE id=?";


    public WorkspaceDBActionsService(DataSource dataSource) {
        ds = dataSource;
    }

    @Override
    public void create(Workspace workspace) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(CREATE);
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
        try (Connection conn = ds.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(UPDATE);
            ps.setString(1, scanner.nextLine());
            ps.setString(2, workspace.getDescription());
            ps.setString(3, String.valueOf(workspace.getVisibility()));
            ps.setObject(4, workspace.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Workspace::update failed");
        }
    }

    @Override
    public Workspace get(UUID id) {
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
    public boolean delete(UUID id) {
        if (get(id) == null) {
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
