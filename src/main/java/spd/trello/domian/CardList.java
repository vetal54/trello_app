package spd.trello.domian;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domian.common.Resource;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "card_list")
public class CardList extends Resource {

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    String name;

    @Column(name = "archived")
    Boolean archived = false;

    @Column(name = "board_id")
    UUID boardId;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(
            name = "card",
            joinColumns = @JoinColumn(name = "cardlist_id")
    )
    @Column(name = "id")
    Set<UUID> cards = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CardList cardList = (CardList) o;
        return getId() != null && Objects.equals(getId(), cardList.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
