package spd.trello.service;

import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;
import spd.trello.repository.WorkspaceRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class WorkspaceService extends AbstractService<Workspace> {

    public WorkspaceService(WorkspaceRepositoryImpl repository) {
        super(repository);
    }

    @Override
    public Workspace create() {
        Workspace workspace = new Workspace();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter workspace name:");
        workspace.setName(scanner.nextLine());
        System.out.println("Enter the email of the user who created the workspace:");
        workspace.setCreateBy(scanner.nextLine());
        System.out.println("Enter visibility type. It can be PRIVATE or PUBLIC:");
        workspace.setVisibility(WorkspaceVisibility.valueOf(scanner.nextLine()));
        System.out.println("Enter descriptions");
        workspace.setDescription(scanner.nextLine());
        print(workspace);
        repository.create(workspace);
        return workspace;
    }

    @Override
    public void print(Workspace workspace) {
        System.out.println(workspace);
    }

    @Override
    public void update(Workspace workspace) {
        repository.update(workspace);
    }

    @Override
    public Workspace findById(UUID id) {
        return repository.getById(id);
    }

    @Override
    public List<Workspace> findAll() {
        return repository.getAll();
    }

    @Override
    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
