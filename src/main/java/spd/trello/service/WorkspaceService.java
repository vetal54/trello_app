package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.repository.WorkspaceRepository;

@Service
public class WorkspaceService extends AbstractResourceService<Workspace, WorkspaceRepository> {

    public WorkspaceService(WorkspaceRepository repository) {
        super(repository);
    }

    public Workspace create(String name, String email, WorkspaceVisibility ws, String description) {
        Workspace workspace = new Workspace();
        workspace.setName(name);
        workspace.setCreateBy(email);
        workspace.setVisibility(ws);
        workspace.setDescription(description);
        return repository.save(workspace);
    }
}
