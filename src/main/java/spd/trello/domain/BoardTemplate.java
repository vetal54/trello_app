package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoardTemplate extends Domain{
    String name;

    public BoardTemplate(String name) {
        this.name = name;
    }
}
