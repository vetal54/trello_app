package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Workspace extends Resource {
    String name;
    List<Board> boards = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    String description;
    WorkspaceVisibility visibility;

    public Workspace(String name, String description, WorkspaceVisibility visibility) {
        this.name = name;
        this.description = description;
        this.visibility = visibility;
    }

    public void addBoard(Board board) {
        board.setWorkspaceId(this.getId());
        boards.add(board);
    }

    public void addMembers(Member member) {
        members.add(member);
    }

    @Override
    public String toString() {
        return "\n" + name + " " + getId();
    }
}
