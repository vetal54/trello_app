package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.WorkspaceRepositoryImpl;

@Service
public class WorkspaceService extends ServiceLayer<Workspace> {

    public WorkspaceService(WorkspaceRepositoryImpl repository) {
        super(repository);
    }

    public Workspace create(String name, String email, WorkspaceVisibility ws, String description) {
        Workspace workspace = new Workspace();
        workspace.setName(name);
        workspace.setCreateBy(email);
        workspace.setVisibility(ws);
        workspace.setDescription(description);
        print(workspace);
        repository.create(workspace);
        return repository.getById(workspace.getId());
    }
}