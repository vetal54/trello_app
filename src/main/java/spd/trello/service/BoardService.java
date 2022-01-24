package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Board;
import spd.trello.repository.BoardRepositoryImpl;

import java.util.UUID;

@Service
public class BoardService extends ServiceLayer<Board> {

    public BoardService(BoardRepositoryImpl repository) {
        super(repository);
    }

    public Board create(String name, String email, UUID id) {
        Board board = new Board();
        board.setName(name);
        board.setCreateBy(email);
        board.setWorkspaceId(id);
        print(board);
        repository.create(board);
        return repository.getById(board.getId());
    }
}
