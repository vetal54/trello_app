package spd.trello.domian;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.domian.common.Resource;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "card")
public class Card extends Resource {

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "archived")
    Boolean archived = false;

    @Column(name = "cardlist_id")
    UUID cardListId;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "card", cascade = CascadeType.ALL)
    @JsonManagedReference
    Reminder reminder;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "card", cascade = CascadeType.ALL)
    @JsonManagedReference
    CheckList checkList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Card card = (Card) o;
        return getId() != null && Objects.equals(getId(), card.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
