package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.domian.Board;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.exeption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM board")
class BoardServiceTest2 {

    @Autowired
    private BoardService service;

    @Autowired
    private WorkspaceService workspaceService;

    private Workspace workspace;

    @BeforeEach
    void init() {
        workspace = workspaceService.create("name", "email", WorkspaceVisibility.PRIVATE, "description");
    }

    @Test
    void boardWasSaved() {
        Board board = service.create("name", "email", "description", BoardVisibility.PRIVATE, workspace.getId());
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
        Board board = service.create("name", "email", "description", BoardVisibility.PRIVATE, workspace.getId());

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
        Board board = service.create("name", "email", "description", BoardVisibility.PRIVATE, workspace.getId());

        Board boardFindById = service.findById(board.getId());

        assertThat(boardFindById).isEqualTo(board);
    }

    @Test
    void boardWasDeleted() {
        Board board = service.create("name", "email", "description", BoardVisibility.PRIVATE, workspace.getId());

        service.delete(board.getId());

        assertThatCode(() -> service.findById(board.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void boardWasUpdated() {
        Board savedBoard = service.create("name", "email", "description", BoardVisibility.PRIVATE, workspace.getId());
        savedBoard.setName("new Name");

        Board updatedBoard = service.update(savedBoard);

        assertThat(updatedBoard.getName()).isEqualTo("new Name");
    }
}
