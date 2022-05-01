package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import spd.trello.domian.Workspace;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.MemberRepository;
import spd.trello.repository.WorkspaceRepository;

import java.util.UUID;

@Service
@Slf4j
public class WorkspaceService extends AbstractResourceService<Workspace, WorkspaceRepository> implements Validator<Workspace> {

    private final MemberRepository memberRepository;

    public WorkspaceService(WorkspaceRepository repository, MemberRepository memberRepository) {
        super(repository);
        this.memberRepository = memberRepository;
    }

    @Override
    public Workspace save(Workspace workspace) throws JsonProcessingException {
        log.info("Try saving workspace");
        validateReference(workspace);
        log.info("Workspace created successfully {}", mapper.writeValueAsString(workspace));
        return repository.save(workspace);
    }

    @Override
    public Workspace update(Workspace workspace) throws JsonProcessingException {
        log.info("Try updating workspace");
        validateReference(workspace);
        log.info("Workspace updated successfully {}", mapper.writeValueAsString(workspace));
        return repository.save(workspace);
    }

    @Override
    public void validateReference(Workspace workspace) {
        log.info("Try checked foreign key");
        for (UUID id : workspace.getMemberIds()) {
            memberRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Member reference not valid. Id not corrected: " + id));
        }
    }
}
