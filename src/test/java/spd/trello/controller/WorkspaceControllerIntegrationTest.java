package spd.trello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.Helper;
import spd.trello.domian.User;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.WorkspaceVisibility;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(statements = "DELETE FROM workspace")
@Sql(statements = "DELETE FROM user_table")
class WorkspaceControllerIntegrationTest extends AbstractIntegrationTest<Workspace> {

    private final String URL_TEMPLATE = "/workspace";

    @Autowired
    private Helper helper;

    @Test
    void create() throws Exception {
        Workspace workspace = new Workspace();
        workspace.setName("name!");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspace.setDescription("description");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, workspace, token);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(workspace.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(workspace.getName(), getValue(mvc, "$.name")),
                () -> assertEquals(workspace.getDescription(), getValue(mvc, "$.description")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void createValidationFailed() throws Exception {
        Workspace workspace = new Workspace();
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspace.setDescription("description");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.create(URL_TEMPLATE, workspace, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("Name should not be empty", getValue(mvc, "$.details.name"))
        );
    }

    @Test
    void delete() throws Exception {
        Workspace workspace = helper.createWorkspace();
        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);
        MvcResult mvc = super.delete(URL_TEMPLATE, workspace.getId(), token);
        assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findById() throws Exception {
        Workspace workspace = helper.createWorkspace();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, workspace.getId(), token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(workspace.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals(workspace.getName(), getValue(mvc, "$.name")),
                () -> assertEquals(workspace.getDescription(), getValue(mvc, "$.description")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void findByIdFailed() throws Exception {
        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.getById(URL_TEMPLATE, UUID.randomUUID(), token);

        assertEquals(HttpStatus.NOT_FOUND.value(), mvc.getResponse().getStatus());
    }

    @Test
    void findAll() throws Exception {
        Workspace firstWorkspace = helper.createWorkspace();
        Workspace secondWorkspace = helper.createWorkspace();

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.findAll(URL_TEMPLATE, token);
        List<Workspace> workspaces = helper.getWorkspacesArray(mvc);

        assertAll(
                () -> assertEquals(MediaType.APPLICATION_JSON.toString(), mvc.getResponse().getContentType()),
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertTrue(workspaces.contains(firstWorkspace)),
                () -> assertTrue(workspaces.contains(secondWorkspace))
        );
    }

    @Test
    void update() throws Exception {
        Workspace workspace = helper.createWorkspace();
        workspace.setName("new Name");
        workspace.setDescription("new Description");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, workspace.getId(), workspace, token);

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), mvc.getResponse().getStatus()),
                () -> assertNotNull(getValue(mvc, "$.id")),
                () -> assertEquals(workspace.getId().toString(), getValue(mvc, "$.id")),
                () -> assertEquals("new Name", getValue(mvc, "$.name")),
                () -> assertEquals("new Description", getValue(mvc, "$.description")),
                () -> assertEquals("admin@gmail.com", getValue(mvc, "$.createBy"))
        );
    }

    @Test
    void updateValidationFailed() throws Exception {
        Workspace workspace = helper.createWorkspace();
        workspace.setName("n");
        workspace.setDescription("new Description");

        User user = helper.createUser();
        String token = helper.createUserAdminAndGetToken(user);

        MvcResult mvc = super.update(URL_TEMPLATE, workspace.getId(), workspace, token);

        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST.value(), mvc.getResponse().getStatus()),
                () -> assertEquals("Validation Failed", getValue(mvc, "$.message")),
                () -> assertEquals("size must be between 2 and 30", getValue(mvc, "$.details.name"))
        );
    }
}
