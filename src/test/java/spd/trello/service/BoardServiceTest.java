package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import spd.trello.domian.Board;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.repository.BoardRepository;
import spd.trello.repository.WorkspaceRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;
    @Mock
    private WorkspaceRepository workspaceRepository;
    private Board board;
    private Workspace workspace;
    private BoardService service;

    void create() {
        board = new Board();
        board.setName("vitaliy");
        board.setCreateBy("@gmail");
        board.setArchived(false);
        board.setVisibility(BoardVisibility.PRIVATE);
        board.setDescription("hello");

        workspace = new Workspace();
        workspace.setName("vitaliy");
        workspace.setCreateBy("@gmail");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspace.setDescription("hello");
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new BoardService(boardRepository);
        create();
    }

    @Test
    void saveBoard() {
        when(workspaceRepository.save(workspace)).thenReturn(workspace);
        Workspace savedWorkspace = workspaceRepository.save(workspace);

        board.setWorkspaceId(workspace.getId());

        when(boardRepository.save(board)).thenReturn(board);
        Board savedBoard = boardRepository.save(board);

        assertThat(savedWorkspace.getId()).isEqualTo(savedBoard.getWorkspaceId());
    }
}