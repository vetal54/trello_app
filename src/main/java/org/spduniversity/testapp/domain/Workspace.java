package org.spduniversity.testapp.domain;

import java.util.ArrayList;
import java.util.List;

public class Workspace {
    private String name;
    private final List<Board> boards = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private String description;
    private WorkspaceVisibility visibility;

    public Workspace(String name, WorkspaceVisibility visibility) {
        this.name = name;
        this.visibility = visibility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void addBoards(Board board) {
        boards.add(board);
    }

    public List<Member> getMembers() {
        return members;
    }

    public void addMembers(Member member) {
        members.add(member);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkspaceVisibility getVisibility() { return visibility; }

    public void setVisibility(WorkspaceVisibility visibility) {
        this.visibility = visibility;
    }
}
