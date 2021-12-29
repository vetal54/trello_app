package spd.trello.service;

import spd.trello.domain.Board;
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

    public void getBoard(BoardDBActionsService bdbas, String id) {
        try {
            Board board = bdbas.get(UUID.fromString(id));
            out.println(board);
        } catch (IllegalStateException e) {
            out.println(e.getMessage());
        }
    }

    public void updateBoard(BoardDBActionsService bdbas, Board board) {
        try {
            bdbas.update(board);
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }

    public void deleteBord(BoardDBActionsService bdbas, String id) {
        try {
            out.println(bdbas.delete(UUID.fromString(id)));
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }
}
