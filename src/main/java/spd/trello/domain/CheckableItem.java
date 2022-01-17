package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckableItem extends Domain {
    String name;
    Boolean checked;

    public CheckableItem(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }
}
