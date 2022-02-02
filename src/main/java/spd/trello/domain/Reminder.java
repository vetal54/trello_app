package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domain.common.Resource;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reminder extends Resource {
    LocalDateTime start;
    LocalDateTime end;
    LocalDateTime remindOn;
    Boolean active = true;
    UUID cardId;
}
