package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domian.common.Domain;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Label extends Domain {
    String name;
    String color;
}
