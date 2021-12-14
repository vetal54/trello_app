package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Card extends Resource {
    String name;
    String description;
    List<Member> assignedMembers = new ArrayList<>();
    List<Label> labels = new ArrayList<>();
    List<Attachment> attachments = new ArrayList<>();
    Boolean active = true;
    List<Comment> comments = new ArrayList<>();
    Reminder reminder;
    List<CheckList> checkList = new ArrayList<>();

    public Card(String name) {
        this.name = name;
    }

    public void addAssignedMembers(Member assignedMember) {
        assignedMembers.add(assignedMember);
    }

    public void addLabels(Label label) {
        labels.add(label);
    }

    public void addAttachments(Attachment attachment) {
        attachments.add(attachment);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addCheckLists(CheckList check) {
        checkList.add(check);
    }

    @Override
    public String toString() {
        return name + ", "
                + "create by: " + getCreateBy()
                + ", id: " + getId()
                + ", time: " + getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                + "\n";
    }
}
