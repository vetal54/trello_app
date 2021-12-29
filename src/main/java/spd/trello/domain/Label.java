package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Label extends Domain {

    String name;
    String color;

    public Label(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
