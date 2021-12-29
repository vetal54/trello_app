package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.domain.Resource;
import spd.trello.domain.Workspace;
import spd.trello.domain.WorkspaceVisibility;

import java.util.Scanner;

public class WorkspaceService extends AbstractService<Workspace> {

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
        return workspace;
    }

    @Override
    public void print(Workspace workspace) {
        System.out.println(workspace);
    }
}
