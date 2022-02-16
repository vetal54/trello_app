package spd.trello.domian.type;

public enum BoardVisibility {
    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC");

    private final String title;

    BoardVisibility(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
