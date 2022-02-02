package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domain.common.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardList extends Resource {
    String name;
    List<Card> cards = new ArrayList<>();
    Boolean active = true;
    UUID boardId;

    public void addCard(Card card) {
        card.setCardListId(this.getId());
        cards.add(card);
    }
}
