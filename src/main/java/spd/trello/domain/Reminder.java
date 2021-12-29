package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reminder extends Resource {

    LocalDateTime start;
    LocalDateTime end;
    LocalDateTime remindOn;
    Boolean active = true;
    UUID cardID;

    public Reminder(LocalDateTime start, LocalDateTime end, LocalDateTime remindOn) {
        this.start = start;
        this.end = end;
        this.remindOn = remindOn;
    }

    @Override
    public String toString() {
        return remindOn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
