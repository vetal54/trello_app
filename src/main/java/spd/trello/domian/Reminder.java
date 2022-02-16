package spd.trello.domian;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.domian.common.Domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "reminder")
public class Reminder extends Domain {

    @Column(name = "start_on")
    Timestamp start;

    @Column(name = "end_on")
    Timestamp end;

    @Column(name = "remind_on")
    Timestamp remindOn;

    @Column(name = "active")
    Boolean active = false;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id")
    @JsonBackReference
    Card card;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reminder reminder = (Reminder) o;
        return getId() != null && Objects.equals(getId(), reminder.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
