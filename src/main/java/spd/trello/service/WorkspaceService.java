package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Workspace;
import spd.trello.repository.WorkspaceRepository;

@Service
public class WorkspaceService extends AbstractResourceService<Workspace, WorkspaceRepository> {

    public WorkspaceService(WorkspaceRepository repository) {
        super(repository);
    }
}
