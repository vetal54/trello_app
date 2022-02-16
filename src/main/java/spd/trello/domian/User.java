package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.domian.common.Domain;

import javax.persistence.Entity;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class User extends Domain {

    String firstName;
    String lastName;
    String email;
    UUID workspaceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
