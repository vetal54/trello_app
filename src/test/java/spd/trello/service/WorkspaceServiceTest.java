package spd.trello.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.WorkspaceRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceServiceTest extends BaseTest {

    private static Workspace workspace;
    private final WorkspaceService service;

    public WorkspaceServiceTest() {
        service = new WorkspaceService(new WorkspaceRepositoryImpl(dataSource));
    }

    @BeforeAll
    static void create() {
        workspace = new Workspace("workspaceName", "New year 2022",WorkspaceVisibility.PUBLIC);
    }

    @Test
    void printWorkspace() {
        assertEquals("\n" + workspace.getName() + " " + workspace.getId(), workspace.toString());
    }

    @Test
    void save() {
        service.repository.create(workspace);
        Workspace byId = service.findById(workspace.getId());
        assertEquals(workspace.getName(), byId.getName());
    }

    @Test
    void testFindById() {
        Workspace findWorkspace = service.findById(workspace.getId());
        assertEquals(workspace.getName(), findWorkspace.getName());
    }

    @Test
    void testFindByIdFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "Workspace not found"
        );
        assertEquals("Workspace with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(workspace);
        workspace.setName("it`s update workspace");
        service.update(workspace);
        Workspace startBoard = service.findById(workspace.getId());
        assertEquals(workspace.getName(), startBoard.getName());
    }

    @Test
    void testDelete() {
        boolean bool = service.delete(workspace.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "Workspace::findWorkspaceById failed"
        );
        assertEquals("Workspace with ID: " + uuid + " doesn't exists", ex.getMessage());
    }
}