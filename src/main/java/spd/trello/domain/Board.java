package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Board extends Resource {
    String name;
    String description;
    List<CardList> cardLists = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    BoardVisibility visibility;
    Boolean active = true;

    public Board(String name, BoardVisibility visibility) {
        this.name = name;
        this.visibility = visibility;
    }

    public void addCardLists(CardList cardList) {
        cardLists.add(cardList);
    }

    public void addMembers(Member member) {
        members.add(member);
    }

    @Override
    public String toString() {
        return name + ", "
                + "create by: " + getCreateBy()
                + ", id: " + getId()
                + ", time: " + getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                + "\n";
    }
}
