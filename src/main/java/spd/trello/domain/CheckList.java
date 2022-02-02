package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import spd.trello.domain.common.Resource;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckList extends Resource {
    String name;
    List<CheckableItem> items = new ArrayList<>();

    public void addItems(CheckableItem item) {
        items.add(item);
    }
}
