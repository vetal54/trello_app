package spd.trello.domian;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import spd.trello.domian.common.Resource;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "comment")
public class Comment extends Resource {

    @Column(name = "context")
    String context;

    @Column(name = "card_id")
    UUID cardId;

    @Column(name = "member_id")
    UUID memberId;


//    @ElementCollection
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @CollectionTable(
//            name = "comment",
//            joinColumns = @JoinColumn(name = "card_id")
//    )
//    @Column(name = "id")
//    List<UUID> attachments = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return getId() != null && Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
