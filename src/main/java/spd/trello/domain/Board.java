package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domain.common.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Board extends Resource {
    String name;
    String description;
    List<CardList> cardLists = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    BoardVisibility visibility;
    Boolean active = true;
    UUID workspaceId;

    public void addCardLists(CardList cardList) {
        cardList.setBoardId(this.getId());
        cardLists.add(cardList);
    }

    public void addMembers(Member member) {
        members.add(member);
    }
}
