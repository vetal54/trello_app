package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends Resource {
    Member member;
    String text;
    LocalDateTime date;
    List<Attachment> attachments = new ArrayList<>();
    UUID cardId;

    public Comment(Member member, String text, LocalDateTime date) {
        this.member = member;
        this.text = text;
        this.date = date;
    }

    public void add(Attachment attachment) {
        attachments.add(attachment);
    }

    @Override
    public String toString() {
        return getText() + ", date: " + getDate();
    }
}
