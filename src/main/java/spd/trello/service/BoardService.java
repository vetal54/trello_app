package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Board;
import spd.trello.repository.BoardRepositoryImpl;

import java.util.UUID;

@Service
public class BoardService extends AbstractService<Board, BoardRepositoryImpl> {

    public BoardService(BoardRepositoryImpl repository) {
        super(repository);
    }

    public Board create(String name, String email, UUID id) {
        Board board = new Board();
        board.setName(name);
        board.setCreateBy(email);
        board.setWorkspaceId(id);
        repository.save(board);
        return repository.getById(board.getId());
    }
}
