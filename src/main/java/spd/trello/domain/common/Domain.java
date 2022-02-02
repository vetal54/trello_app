package spd.trello.domain.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Domain {
    @Id
    UUID id = UUID.randomUUID();
}
