package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardTemplate {

    String title;

    public CardTemplate(String title) {
        this.title = title;
    }
}
