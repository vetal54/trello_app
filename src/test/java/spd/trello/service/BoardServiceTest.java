package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.Board;
import spd.trello.exeption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM board")
class BoardServiceTest {

    @Autowired
    private BoardService service;
    @Autowired
    private Helper helper;

    @Test
    void boardWasSaved() {
        Board board = helper.createBoard();
        Board boardSave = service.findById(board.getId());
        assertThat(boardSave).isEqualTo(board);
    }

    @Test
    void emptyListOfBoardsIsReturned() {
        List<Board> boards = service.findAll();

        assertThat(boards).isEmpty();
    }

    @Test
    void notEmptyListOfBoardsIsReturned() {
        Board board = helper.createBoard();

        List<Board> boards = service.findAll();

        assertThat(boards).isNotEmpty();
    }

    @Test
    void boardWasNotFoundById() {
        assertThatCode(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void boardWasFoundById() {
        Board board = helper.createBoard();

        Board boardFindById = service.findById(board.getId());

        assertThat(boardFindById).isEqualTo(board);
    }

    @Test
    void boardWasDeleted() {
        Board board = helper.createBoard();

        service.delete(board.getId());

        assertThatCode(() -> service.findById(board.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void boardWasUpdated() {
        Board savedBoard = helper.createBoard();
        savedBoard.setName("new Name");

        Board updatedBoard = service.update(savedBoard);

        assertThat(updatedBoard.getName()).isEqualTo("new Name");
    }
}
