package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Board;
import spd.trello.repository.BoardRepository;

import java.util.UUID;

@Service
public class BoardService extends AbstractService<Board, BoardRepository> {

    public BoardService(BoardRepository repository) {
        super(repository);
    }

    public Board create(String name, String email, UUID id) {
        Board board = new Board();
        board.setName(name);
        board.setCreateBy(email);
        board.setWorkspaceId(id);
        return repository.save(board);
    }
}
