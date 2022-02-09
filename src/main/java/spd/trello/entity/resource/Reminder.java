package spd.trello.entity.resource;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.entity.common.Resource;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Reminder extends Resource {
    Timestamp start;
    Timestamp end;
    Timestamp remindOn;
    Boolean active = true;
    UUID cardId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reminder reminder = (Reminder) o;
        return getId() != null && Objects.equals(getId(), reminder.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
