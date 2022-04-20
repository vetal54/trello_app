package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domian.common.Domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attachment_file")
public class AttachmentFile extends Domain {
    String name;
    String type;
    byte[] file;
    UUID attachmentId;
}
