package org.spduniversity.testapp.domain;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private String name;
    private String description;
    private final List<CardList> cardLists = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private BoardVisibilityEnum visibility;
    private boolean favouriteStatus;
    private boolean isArchived;

    public Board(String name, BoardVisibilityEnum visibility) {
        this.name = name;
        this.visibility = visibility;
        favouriteStatus = true;
        isArchived = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CardList> getCardLists() {
        return cardLists;
    }

    public void addCardLists(CardList cardList) {
        cardLists.add(cardList);
    }

    public List<Member> getMembers() {
        return members;
    }

    public void addMembers(Member member) {
        members.add(member);
    }

    public BoardVisibilityEnum getVisibility() {
        return visibility;
    }

    public void setVisibility(BoardVisibilityEnum visibility) {
        this.visibility = visibility;
    }

    public boolean isFavouriteStatus() {
        return favouriteStatus;
    }

    public void setFavouriteStatus(boolean favouriteStatus) {
        this.favouriteStatus = favouriteStatus;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
