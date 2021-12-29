package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.TimeZone;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Domain {

    String firstName;
    String lastName;
    String email;
    TimeZone timeZone;
    UUID workspace_id;

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
