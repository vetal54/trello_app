package spd.trello.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.BoardRepositoryImpl;
import spd.trello.repository.WorkspaceRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest extends BaseTest {
    private static Board board;
    private final BoardService service;

    public BoardServiceTest() {
        service = new BoardService(new BoardRepositoryImpl(dataSource));
    }

    @BeforeAll
    static void create() {
        Workspace workspace = new Workspace("workspaceName", "New year 2022", WorkspaceVisibility.PUBLIC);
        WorkspaceService ws = new WorkspaceService(new WorkspaceRepositoryImpl(dataSource));
        ws.repository.create(workspace);
        board = new Board("boardName", "new Board", BoardVisibility.PRIVATE);
        board.setWorkspaceId(workspace.getId());
    }

    @Test
    void printBoard() {
        assertEquals("\n" + board.getName() + ", id: " + board.getId(), board.toString());
    }

    @Test
    void save() {
        service.repository.create(board);
        Board byId = service.findById(board.getId());
        assertEquals(board.getName(), byId.getName());
    }

    @Test
    void testFindById() {
        Board findBoard = service.findById(board.getId());
        assertEquals(board.getName(), findBoard.getName());
    }

    @Test
    void testFindByIdFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "Board not found"
        );
        assertEquals("Board with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(board);
        board.setName("it`s update board");
        service.update(board);
        Board startBoard = service.findById(board.getId());
        assertEquals(board.getName(), startBoard.getName());
    }

    @Test
    void testDelete() {
        boolean bool = service.delete(board.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "Board::findBoardById failed"
        );
        assertEquals("Board with ID: " + uuid + " doesn't exists", ex.getMessage());
    }
}