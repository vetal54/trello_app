package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spd.trello.domian.Board;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.BoardRepository;
import spd.trello.repository.MemberRepository;
import spd.trello.repository.WorkspaceRepository;

import java.util.UUID;

@Service
@Slf4j
public class BoardService extends AbstractResourceService<Board, BoardRepository> implements Validator<Board> {

    private final WorkspaceRepository workspaceRepository;
    private final MemberRepository memberRepository;

    public BoardService(BoardRepository repository, WorkspaceRepository workspaceRepository, MemberRepository memberRepository) {
        super(repository);
        this.workspaceRepository = workspaceRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Board save(Board board) throws JsonProcessingException {
        log.info("Try saving board");
        validateReference(board);
        log.info("Board created successfully {}", mapper.writeValueAsString(board));
        return repository.save(board);
    }

    @Override
    public Board update(Board board) throws JsonProcessingException {
        log.info("Try updating board");
        validateReference(board);
        log.info("Board updated successfully {}", mapper.writeValueAsString(board));
        return repository.save(board);
    }

    @Override
    public void validateReference(Board board) {
        log.info("Try checked foreign key");
        workspaceRepository.findById(board.getWorkspaceId()).orElseThrow(() ->
                new ResourceNotFoundException("Workspace reference not valid. Id not corrected: " + board.getWorkspaceId()));
        for (UUID id : board.getMemberIds()) {
            memberRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Member reference not valid. Id not corrected: " + id));
        }
    }
}
