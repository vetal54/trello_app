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
    User createBy;
    User updateBy;
    LocalDateTime createDate = LocalDateTime.now();
    LocalDateTime updateDate;
}
