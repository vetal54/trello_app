package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.WorkspaceRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceServiceTest extends BaseTest {

    private Workspace workspace;
    private final WorkspaceService service;

    public WorkspaceServiceTest() {
        service = new WorkspaceService(new WorkspaceRepositoryImpl(jdbcTemplate));
    }

    @BeforeEach
    void create() {
        workspace = service.create("work", "email", WorkspaceVisibility.PRIVATE, "hello");
    }

    @Test
    void save() {
        Workspace byId = service.repository.save(workspace);
        assertEquals(workspace.getName(), byId.getName());
    }

    @Test
    void testFindById() {
        Workspace findWorkspace = service.repository.save(workspace);
        assertEquals(workspace.getName(), findWorkspace.getName());
    }

    @Test
    void testUpdate() {
        Workspace workspace1 = service.repository.save(workspace);
        workspace.setName("it`s update workspace");
        service.update(workspace);
        Workspace startBoard = service.findById(workspace.getId());
        assertEquals(workspace.getName(), startBoard.getName());
    }

    @Test
    void testFindAll() {
       Workspace workspace1 = service.repository.save(workspace);
       Workspace workspace2 = service.create("work", "mail", WorkspaceVisibility.PRIVATE, "hello");
       Workspace workspace3 = service.create("work2", "mail2", WorkspaceVisibility.PUBLIC, "hello2");
        assertEquals(3, service.findAll().size());
    }

    @Test
    void testDeleteTrue() {
       Workspace workspace1 = service.repository.save(workspace);
        boolean bool = service.delete(workspace.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFalse() {
        UUID uuid = UUID.randomUUID();
        boolean bool = service.delete(uuid);
        assertFalse(bool);
    }
}