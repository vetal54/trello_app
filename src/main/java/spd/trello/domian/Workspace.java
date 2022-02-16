package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domian.common.Resource;
import spd.trello.domian.type.WorkspaceVisibility;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "workspace")
public class Workspace extends Resource {

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "visibility")
    WorkspaceVisibility visibility;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "board",
            joinColumns = @JoinColumn(name = "workspace_id")
    )
    @Column(name = "id")
    Set<UUID> boards = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Workspace workspace = (Workspace) o;
        return getId() != null && Objects.equals(getId(), workspace.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
