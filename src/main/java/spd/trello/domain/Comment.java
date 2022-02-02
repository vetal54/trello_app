package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domain.common.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends Resource {
    Member member;
    String text;
    LocalDateTime date;
    List<Attachment> attachments = new ArrayList<>();
    UUID cardId;

    public void add(Attachment attachment) {
        attachments.add(attachment);
    }
}
