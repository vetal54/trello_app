package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardList extends Resource {
    String name;
    List<Card> cards = new ArrayList<>();
    Boolean active = true;
    UUID boardId;

    public CardList(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
        card.setCardListId(this.getId());
        cards.add(card);
    }

    @Override
    public String toString() {
        return name + ", id: " + getId();
    }
}
