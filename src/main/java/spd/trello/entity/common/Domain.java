package spd.trello.entity.common;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public class Domain {
    @Id
    UUID id = UUID.randomUUID();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Domain domain = (Domain) o;
        return id != null && Objects.equals(id, domain.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
