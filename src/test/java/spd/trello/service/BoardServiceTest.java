package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import spd.trello.domian.Board;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.BoardRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository repository;
    private Board board;
    private BoardService service;

    void create() {
        board = new Board();
        board.setName("vitaliy");
        board.setCreateBy("@gmail");
        board.setArchived(false);
        board.setVisibility(BoardVisibility.PRIVATE);
        board.setDescription("hello");
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new BoardService(repository);
        create();
    }

    @Test
    void boardSave() {
        when(repository.save(Mockito.any(Board.class))).thenReturn(board);
        Board savedBoard = repository.save(board);
        assertThat(savedBoard).isEqualTo(board);
    }

    @Test
    void emptyListOfBoards() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Board> boards = service.findAll();
        assertThat(boards).isEmpty();
    }

    @Test
    void oneElementOfListBoards() {
        when(repository.findAll()).thenReturn(
                List.of(
                        board
                )
        );
        List<Board> boards = service.findAll();
        assertThat(boards).isEqualTo(List.of(board));
    }

    @Test
    void boardWasNotFoundById() {
        when(repository.findById(board.getId()))
                .thenReturn(Optional.empty());

        assertThatCode(() -> service.findById(board.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void boardWasFoundById() {
        when(repository.findById(board.getId())).thenReturn(
                Optional.of(board)
        );
        Board boardFindById = service.findById(board.getId());
        assertThat(boardFindById).isEqualTo(board);
    }

    @Test
    void boardWasDeleted() {
        service.delete(board.getId());
        verify(repository).deleteById(board.getId());
    }

    @Test
    void boardWasUpdated() {
        when(repository.save(board))
                .thenReturn(board);

        board.setName("new Name");
        Board updatedBoard = service.save(board);
        assertThat(updatedBoard.getName()).isEqualTo(board.getName());
    }
}