package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardList extends Resource {
    String name;
    List<Card> cards = new ArrayList<>();
    Boolean active = true;

    public CardList(String name) {
        this.name = name;
    }

    public void addCards(Card card) {
        cards.add(card);
    }
}
