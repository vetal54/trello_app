package org.spduniversity.testapp.domain;

public class CardTemplate {
    private String title;

    public CardTemplate(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void renameTitle(String title) {
        this.title = title;
    }
}
