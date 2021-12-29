package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member extends Domain {

    User user;
    Role role;

    public Member(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}
