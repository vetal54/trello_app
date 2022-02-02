package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
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
    private Board board;
    private final BoardService service;

    public BoardServiceTest() {
        service = new BoardService(new BoardRepositoryImpl(jdbcTemplate));
    }

    @BeforeEach
    void create() {
        WorkspaceService ws = new WorkspaceService(new WorkspaceRepositoryImpl(jdbcTemplate));
        Workspace workspace = ws.create("work", "email", WorkspaceVisibility.PRIVATE, "Hello");
        board = service.create("board", "email", workspace.getId());
    }

    @Test
    void save() {
        Board byId = service.repository.save(board);
        assertEquals(board.getName(), byId.getName());
    }

    @Test
    void testFindById() {
        Board findBoard = service.repository.save(board);
        assertEquals(board.getName(), findBoard.getName());
    }

    @Test
    void testUpdate() {
        Board board12 = service.repository.save(board);
        board.setName("it`s update board");
        service.update(board);
        Board startBoard = service.findById(board.getId());
        assertEquals(board.getName(), startBoard.getName());
    }

    @Test
    void testFindAll() {
        Board board1 = service.repository.save(board);
        service.create("board", "v@gmail.com", board.getWorkspaceId());
        service.create("board2", "d@gmail.com", board.getWorkspaceId());
        assertEquals(3, service.findAll().size());
    }

    @Test
    void testDelete() {
       Board board1 = service.repository.save(board);
        boolean bool = service.delete(board.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        assertFalse(service.delete(uuid));
    }
}