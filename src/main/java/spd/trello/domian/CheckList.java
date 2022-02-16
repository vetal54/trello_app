package spd.trello.domian;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.domian.common.Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "check_list")
public class CheckList extends Domain {

    @Column(name = "name")
    String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id")
    @JsonBackReference
    Card card;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "checkList", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("checkList")
    List<Item> items = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CheckList checkList = (CheckList) o;
        return getId() != null && Objects.equals(getId(), checkList.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
