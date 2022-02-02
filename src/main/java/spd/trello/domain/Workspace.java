package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domain.common.Resource;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Workspace extends Resource {
    String name;
    List<Board> boards = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    String description;
    WorkspaceVisibility visibility;

    public void addBoard(Board board) {
        board.setWorkspaceId(this.getId());
        boards.add(board);
    }

    public void addMembers(Member member) {
        members.add(member);
    }
}
