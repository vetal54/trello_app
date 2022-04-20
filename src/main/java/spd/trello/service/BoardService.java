package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Board;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.repository.BoardRepository;

import java.util.UUID;

@Service
public class BoardService extends AbstractResourceService<Board, BoardRepository> {

    public BoardService(BoardRepository repository) {
        super(repository);
    }
}
