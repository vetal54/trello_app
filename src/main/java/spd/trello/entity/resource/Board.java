package spd.trello.entity.resource;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.entity.BoardVisibility;
import spd.trello.entity.common.Resource;

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
    @Column(name = "active")
    Boolean active = true;
    @Column(name = "workspace_id")
    UUID workspaceId;
    @OneToMany(targetEntity = CardList.class,
            mappedBy = "boardId",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    List<CardList> cardLists = new ArrayList<>();

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
