package spd.trello.entity;

public enum WorkspaceVisibility {
    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC");

    private final String title;

    WorkspaceVisibility(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
