package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domain.common.Domain;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckableItem extends Domain {
    String name;
    Boolean checked;
}
