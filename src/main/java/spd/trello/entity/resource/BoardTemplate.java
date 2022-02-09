package spd.trello.entity.resource;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.entity.common.Domain;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardTemplate extends Domain {
    String name;
}
