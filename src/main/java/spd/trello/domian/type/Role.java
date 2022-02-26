package spd.trello.domian.type;

public enum Role {
    GUEST("GUEST"),
    MEMBER("MEMBER"),
    ADMIN("ADMIN");

    private String title;

    Role(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
