package spd.trello.entity.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.entity.common.Domain;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Label extends Domain {
    String name;
    String color;
}
