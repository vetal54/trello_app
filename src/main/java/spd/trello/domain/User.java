package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domain.common.Domain;

import java.util.TimeZone;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends Domain {
    String firstName;
    String lastName;
    String email;
    TimeZone timeZone;
    UUID workspaceId;
}
