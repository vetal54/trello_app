package spd.trello.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;
import spd.trello.repository.BoardRepositoryImpl;

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
        board = new Board("boardName", BoardVisibility.PRIVATE);
        board.setDescription("new Board");
        board.setWorkspaceId(UUID.fromString("eb3709a6-15d4-4759-a4e6-29204803b69d"));
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