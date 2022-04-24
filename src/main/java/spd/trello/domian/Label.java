package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.domian.common.Domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "label")
public class Label extends Domain {

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 2, max = 30)
    String name;

    @NotEmpty(message = "Color should not be empty!")
    @Size(min = 5, max = 30)
    String color;

    UUID cardId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Label label = (Label) o;
        return getId() != null && Objects.equals(getId(), label.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
