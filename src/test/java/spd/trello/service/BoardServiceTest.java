package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;
import spd.trello.repository.BoardRepositoryImpl;

import java.util.Collections;
import java.util.Optional;
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
        board = new Board("boardName", BoardVisibility.PRIVATE);
        board.setDescription("new Board");
        board.setWorkspace_id(UUID.fromString("eb3709a6-15d4-4759-a4e6-29204803b69d"));
    }

    @Test
    void testBoard() {
        assertNotNull(board);
        assertAll(
                () -> assertNotNull(board.getCreateDate()),
                () -> assertNull(board.getUpdateDate()),
                () -> assertEquals("boardName", board.getName()),
                () -> assertEquals(BoardVisibility.PRIVATE, board.getVisibility()),
                () -> assertEquals(Collections.emptyList(), board.getCardLists())
        );
    }

    @Test
    void printBoard() {
        assertEquals("\n" + board.getName() + ", id: " + board.getId(), board.toString());
    }

    @Test
    void save() {
        service.repository.create(board);
        Optional<Board> byId = service.findById(board.getId());
        assertNotNull(byId);
    }

    @Test
    void testFindById() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "Board not found"
        );
        assertEquals("Board with ID: " + uuid.toString() + " doesn't exists", ex.getMessage());
    }


    @Test
    void testDelete() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "Board::findBoardById failed"
        );
        assertEquals("Board with ID: " + uuid.toString() + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(board);
        board.setName("it`s update board");
        service.update(board);
        Optional<Board> startBoard = service.findById(board.getId());
        assertEquals("it`s update board", startBoard.get().getName());
    }
}