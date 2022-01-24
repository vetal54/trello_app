package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.BoardRepositoryImpl;
import spd.trello.repository.WorkspaceRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest extends BaseTest {
    private Board board;
    private final BoardService service;

    public BoardServiceTest() {
        service = new BoardService(new BoardRepositoryImpl(dataSource));
    }

    @BeforeEach
    void create() {
        Workspace workspace = new Workspace("workspaceName", "New year 2022", WorkspaceVisibility.PUBLIC);
        WorkspaceService ws = new WorkspaceService(new WorkspaceRepositoryImpl(dataSource));
        ws.repository.create(workspace);
        board = new Board("boardName", "new Board", BoardVisibility.PRIVATE);
        board.setWorkspaceId(workspace.getId());
    }

    @Test
    void printBoard() {
        assertEquals(board.getName() + ", id: " + board.getId(), board.toString());
    }

    @Test
    void save() {
        service.repository.create(board);
        Board byId = service.findById(board.getId());
        assertEquals(board.getName(), byId.getName());
    }

    @Test
    void testFindById() {
        service.repository.create(board);
        Board findBoard = service.findById(board.getId());
        assertEquals(board.getName(), findBoard.getName());
    }

    @Test
    void testFindByIdFailed() {
        UUID uuid = UUID.randomUUID();
        EmptyResultDataAccessException ex = assertThrows(
                EmptyResultDataAccessException.class,
                () -> service.findById(uuid),
                "Board not found"
        );
        assertEquals("Incorrect result size: expected 1, actual 0", ex.getMessage());
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
    void testFindAll() {
        service.repository.create(board);
        service.create("board", "v@gmail.com", board.getWorkspaceId());
        service.create("board2", "d@gmail.com", board.getWorkspaceId());
        assertEquals(3 , service.findAll().size());
    }

    @Test
    void testDelete() {
        service.repository.create(board);
        boolean bool = service.delete(board.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        assertFalse(service.delete(uuid));
    }
}