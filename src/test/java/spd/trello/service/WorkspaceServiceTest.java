package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.exeption.ResourceNotFoundException;

import javax.validation.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM workspace")
class WorkspaceServiceTest {

    @Autowired
    private WorkspaceService service;
    @Autowired
    private Helper helper;

    @Test
    void workspaceWasSaved() {
        Workspace workspace = helper.createWorkspace();
        Workspace workspaceSave = service.findById(workspace.getId());
        assertThat(workspaceSave).isEqualTo(workspace);
    }

    @Test
    void workspaceNotSavedEmptyName() {
        Workspace workspace = new Workspace();
        workspace.setName("");
        workspace.setCreateBy("string@gmail.com");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspace.setDescription("description");

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<Workspace>> violations = validator.validate(workspace);

        assertFalse(violations.isEmpty());
    }

    @Test
    void emptyListOfWorkspacesIsReturned() {
        List<Workspace> workspaces = service.findAll();

        assertThat(workspaces).isEmpty();
    }

    @Test
    void notEmptyListOfWorkspacesIsReturned() {
        Workspace workspace = helper.createWorkspace();

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
        Workspace workspace = helper.createWorkspace();

        Workspace workspaceFindById = service.findById(workspace.getId());

        assertThat(workspaceFindById).isEqualTo(workspace);
    }

    @Test
    void workspaceWasDeleted() {
        Workspace workspace = helper.createWorkspace();

        service.delete(workspace.getId());

        assertThatCode(() -> service.findById(workspace.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void workspaceWasUpdated() {
        Workspace savedWorkspace = helper.createWorkspace();
        savedWorkspace.setName("new Name");

        Workspace updatedWorkspace = service.update(savedWorkspace);

        assertThat(updatedWorkspace.getName()).isEqualTo("new Name");
    }
}
