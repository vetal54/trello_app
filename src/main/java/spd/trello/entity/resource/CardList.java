package spd.trello.entity.resource;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.entity.common.Resource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "card_list")
public class CardList extends Resource {
    @Column(name = "name")
    String name;
    @Column(name = "active")
    Boolean active = true;
    @Column(name = "board_id")
    UUID boardId;
    @OneToMany(targetEntity = Card.class,
            mappedBy = "cardListId",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    List<Card> cards = new ArrayList<>();

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
