package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.File;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attachment extends Resource {

    String link;
    String name;
    File file;

    public Attachment(String link, String name, File file) {
        this.link = link;
        this.name = name;
        this.file = file;
    }
}

