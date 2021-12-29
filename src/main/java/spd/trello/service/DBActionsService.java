package spd.trello.service;

import spd.trello.domain.Workspace;

import java.util.UUID;

import static java.lang.System.out;

public class DBActionsService {

    public void getWorkspace(WorkspaceDBActionsService wdbas, String id) {
        try {
            Workspace workspace = wdbas.get(UUID.fromString(id));
            out.println(workspace);
        } catch (IllegalStateException e) {
            out.println(e.getMessage());
        }
    }

    public void updateWorkspace(WorkspaceDBActionsService wdbas, Workspace workspace) {
        try {
            wdbas.update(workspace);
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    public void deleteWorkspace(WorkspaceDBActionsService wdbas, String id) {
        try {
            out.println(wdbas.delete(UUID.fromString(id)));
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }
}
