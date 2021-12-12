package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.TimeZone;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Domain {
    String firstName;
    String lastName;
    String email;
    TimeZone timeZone;

    public User(String firstName, String lastName, String email, TimeZone timeZone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.timeZone = timeZone;
    }

    @Override
    public String toString() {
        return firstName;
    }
}
