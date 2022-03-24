package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domian.common.Resource;
import spd.trello.domian.type.BoardVisibility;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "board")
public class Board extends Resource {

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    BoardVisibility visibility;

    @Column(name = "archived")
    Boolean archived = false;

    @Column(name = "workspace_id")
    UUID workspaceId;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "board_to_member",
            joinColumns = @JoinColumn(name = "board_id")
    )
    @Column(name = "member_id")
    Set<UUID> memberIds = new HashSet<>();

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
