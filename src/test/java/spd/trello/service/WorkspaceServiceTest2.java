package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.exeption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM workspace")
class WorkspaceServiceTest2 {

    @Autowired
    private WorkspaceService service;

    @Test
    void workspaceWasSaved() {
        Workspace workspace = service.create("name", "email", WorkspaceVisibility.PRIVATE, "description");
        Workspace workspaceSave = service.findById(workspace.getId());
        assertThat(workspaceSave).isEqualTo(workspace);
    }

    @Test
    void emptyListOfWorkspacesIsReturned() {
        List<Workspace> workspaces = service.findAll();

        assertThat(workspaces).isEmpty();
    }

    @Test
    void notEmptyListOfWorkspacesIsReturned() {
        Workspace workspace = service.create("name", "email", WorkspaceVisibility.PRIVATE, "description");

        List<Workspace> workspaces = service.findAll();

        assertThat(workspaces).isNotEmpty();
    }

    @Test
    void workspaceWasNotFoundById() {
        assertThatCode(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void workspaceWasFoundById() {
        Workspace workspace = service.create("name", "email", WorkspaceVisibility.PRIVATE, "description");

        Workspace workspaceFindById = service.findById(workspace.getId());

        assertThat(workspaceFindById).isEqualTo(workspace);
    }

    @Test
    void workspaceWasDeleted() {
        Workspace workspace = service.create("name", "email", WorkspaceVisibility.PRIVATE, "description");

        service.delete(workspace.getId());

        assertThatCode(() -> service.findById(workspace.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void workspaceWasUpdated() {
        Workspace savedWorkspace = service.create("name", "email", WorkspaceVisibility.PRIVATE, "description");
        savedWorkspace.setName("new Name");

        Workspace updatedWorkspace = service.update(savedWorkspace);

        assertThat(updatedWorkspace.getName()).isEqualTo("new Name");
    }
}
