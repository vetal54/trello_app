package spd.trello.domian;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domian.common.Resource;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "card")
public class Card extends Resource {

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    String name;

    @NotEmpty(message = "Description should not be empty")
    @Column(name = "description")
    String description;

    @Column(name = "archived")
    Boolean archived = false;

    @Column(name = "cardlist_id")
    UUID cardListId;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "card_to_member",
            joinColumns = @JoinColumn(name = "card_id")
    )
    @Column(name = "member_id")
    Set<UUID> memberIds = new HashSet<>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "card_to_label",
            joinColumns = @JoinColumn(name = "card_id")
    )
    @Column(name = "label_id")
    Set<UUID> labelIds = new HashSet<>();

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
