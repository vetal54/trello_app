package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.WorkspaceRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceServiceTest extends BaseTest {

    private Workspace workspace;
    private final WorkspaceService service;

    public WorkspaceServiceTest() {
        service = new WorkspaceService(new WorkspaceRepositoryImpl(dataSource));
    }

    @BeforeEach
    void create() {
        workspace = new Workspace("workspaceName", "New year 2022",WorkspaceVisibility.PUBLIC);
    }

    @Test
    void printWorkspace() {
        assertEquals(workspace.getName() + " " + workspace.getId(), workspace.toString());
    }

    @Test
    void save() {
        service.repository.create(workspace);
        Workspace byId = service.findById(workspace.getId());
        assertEquals(workspace.getName(), byId.getName());
    }

    @Test
    void testFindById() {
        service.repository.create(workspace);
        Workspace findWorkspace = service.findById(workspace.getId());
        assertEquals(workspace.getName(), findWorkspace.getName());
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
    void testFindAll() {
        service.repository.create(workspace);
        service.create("work", "mail", WorkspaceVisibility.PRIVATE, "hello");
        service.create("work2", "mail2", WorkspaceVisibility.PUBLIC, "hello2");
        assertEquals(3, service.findAll().size());
    }

    @Test
    void testDeleteTrue() {
        service.repository.create(workspace);
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