package spd.trello.domian;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domian.common.Resource;
import spd.trello.domian.type.WorkspaceVisibility;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "workspace")
public class Workspace extends Resource {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30)
    @Column(name = "name")
    String name;

    @NotEmpty(message = "Description should not be empty")
    @Size(min = 10, max = 200)
    @Column(name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    WorkspaceVisibility visibility;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "workspace_to_member",
            joinColumns = @JoinColumn(name = "workspace_id")
    )
    @Column(name = "member_id")
    Set<UUID> memberIds = new HashSet<>();

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
