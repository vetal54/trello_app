package org.spduniversity.testapp.domain;

import java.util.ArrayList;
import java.util.List;

public class CheckList {
    private String name;
    private final List<CheckableItem> items = new ArrayList<>();

    public CheckList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CheckableItem> getItems() {
        return items;
    }

    public void addItems(CheckableItem item) {
        items.add(item);
    }
}
