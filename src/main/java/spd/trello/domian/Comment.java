package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.domian.common.Resource;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Comment extends Resource {

    String text;
    Timestamp date;
    UUID cardId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return getId() != null && Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
