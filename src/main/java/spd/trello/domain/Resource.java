package spd.trello.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Resource extends Domain {
    String createBy;
    String updateBy;
    LocalDateTime createDate = LocalDateTime.now();
    LocalDateTime updateDate;
}
