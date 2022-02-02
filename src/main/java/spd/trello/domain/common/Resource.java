package spd.trello.domain.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import spd.trello.domain.common.Domain;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Resource extends Domain {
    String createBy;
    String updateBy;
    LocalDateTime createDate = LocalDateTime.now();
    LocalDateTime updateDate;
}
