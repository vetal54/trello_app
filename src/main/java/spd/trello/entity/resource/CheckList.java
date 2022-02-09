package spd.trello.entity.resource;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.entity.common.Resource;

import javax.persistence.Entity;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CheckList extends Resource {
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CheckList checkList = (CheckList) o;
        return getId() != null && Objects.equals(getId(), checkList.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
