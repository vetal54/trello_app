package spd.trello.entity.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.entity.Role;
import spd.trello.entity.common.Domain;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member extends Domain {
    User user;
    Role role;
    UUID boardId;
}
