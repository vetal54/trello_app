package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domian.common.Resource;

import java.io.File;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attachment extends Resource {
    String link;
    String name;
    File file;
}

