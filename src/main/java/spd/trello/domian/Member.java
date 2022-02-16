package spd.trello.domian;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domian.common.Domain;
import spd.trello.domian.type.Role;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member extends Domain {
    User user;
    Role role;
    UUID boardId;
}
