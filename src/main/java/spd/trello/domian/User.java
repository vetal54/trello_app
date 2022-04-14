package spd.trello.domian;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import spd.trello.annotation.EmailValidation;
import spd.trello.domian.common.Domain;
import spd.trello.domian.type.Role;
import spd.trello.domian.type.Status;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_table")
public class User extends Domain {

    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 30)
    @Column(name = "first_name")
    String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 2, max = 30)
    @Column(name = "last_name")
    String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    Role role = Role.ADMIN;

    @EmailValidation
    @NotEmpty(message = "Email should not be empty")
    @Column(name = "email")
    String email;

    @NotEmpty
    @Column(name = "password")
    String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    Status status = Status.ACTIVE;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
