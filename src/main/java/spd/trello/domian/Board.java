package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domian.common.Resource;
import spd.trello.domian.type.BoardVisibility;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "board")
public class Board extends Resource {

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "visibility")
    BoardVisibility visibility;

    @Column(name = "archived")
    Boolean archived = false;

    @Column(name = "workspace_id")
    UUID workspaceId;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "card_list",
            joinColumns = @JoinColumn(name = "board_id")
    )
    @Column(name = "id")
    List<UUID> cardLists = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Board board = (Board) o;
        return getId() != null && Objects.equals(getId(), board.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
