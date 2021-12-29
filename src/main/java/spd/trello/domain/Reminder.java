package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reminder extends Resource {

    LocalDateTime start;
    LocalDateTime end;
    LocalDateTime remindOn;
    Boolean active = true;

    public Reminder(LocalDateTime start, LocalDateTime end, LocalDateTime remindOn) {
        this.start = start;
        this.end = end;
        this.remindOn = remindOn;
    }
}
