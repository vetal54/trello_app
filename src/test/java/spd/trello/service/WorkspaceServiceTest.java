package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.WorkspaceRepositoryImpl;

import java.util.Collections;
import java.util.Optional;
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
        workspace = new Workspace("workspaceName", WorkspaceVisibility.PUBLIC);
        workspace.setDescription("New year 2022");
    }

    @Test
    void createWorkspace() {
        assertNotNull(workspace);
        assertAll(
                () -> assertNotNull(workspace.getCreateDate()),
                () -> assertNull(workspace.getUpdateDate()),
                () -> assertEquals("workspaceName", workspace.getName()),
                () -> assertEquals(WorkspaceVisibility.PUBLIC, workspace.getVisibility()),
                () -> assertEquals("New year 2022", workspace.getDescription()),
                () -> assertEquals(Collections.emptyList() ,workspace.getBoards())
        );
    }

    @Test
    void printWorkspace() {
        assertEquals("\n" + workspace.getName() + " " +  workspace.getId(), workspace.toString());
    }

    @Test
    void save() {
        service.repository.create(workspace);
        Optional<Workspace> byId = service.findById(workspace.getId());
        assertNotNull(byId);
    }

    @Test
    void testFindById() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "CardList not found"
        );
        assertEquals("Workspace with ID: " + uuid + " doesn't exists", ex.getMessage());
    }


    @Test
    void testDelete() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "Workspace::findCardListById failed"
        );
        assertEquals("Workspace with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(workspace);
        workspace.setName("it`s update workspace");
        service.update(workspace);
        Optional<Workspace> startBoard = service.findById(workspace.getId());
        assertEquals("it`s update workspace", startBoard.get().getName());
    }
}