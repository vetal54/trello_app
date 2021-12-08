package org.spduniversity.testapp.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Comment {
    private Member member;
    private String text;
    private LocalDateTime date;
    private final List<Attachment> attachments = new ArrayList<>();

    public Comment(Member member, String text, LocalDateTime date) {
        this.member = member;
        this.text = text;
        this.date = date;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void add(Attachment attachment) {
        attachments.add(attachment);
    }
}
