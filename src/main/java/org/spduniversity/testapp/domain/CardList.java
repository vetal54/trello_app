package org.spduniversity.testapp.domain;

import java.util.ArrayList;
import java.util.List;

public class CardList {
    private String name;
    private final List<Card> cards = new ArrayList<>();
    private boolean isActive;

    public CardList(String name) {
        this.name = name;
        isActive = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void addCards(Card card) {
        cards.add(card);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
