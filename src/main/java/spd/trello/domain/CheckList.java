package spd.trello.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckList extends Resource {

    String name;
    List<CheckableItem> items = new ArrayList<>();

    public CheckList(String name) {
        this.name = name;
    }

    public void addItems(CheckableItem item) {
        items.add(item);
    }
}
