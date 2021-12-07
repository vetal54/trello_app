package org.spduniversity.testapp.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Card {
    private String name;
    private String description;
    private final List<Member> assignedMembers = new ArrayList<>();
    private final List<Label> labels = new ArrayList<>();
    private final List<Attachment> attachments = new ArrayList<>();
    private boolean isArchived;
    private final List<Comment> comments = new ArrayList<>();
    private Reminder reminder;
    private final List<CheckList> checkList = new ArrayList<>();
    private LocalDateTime creationDate;

    public Card(String name) {
        this.name = name;
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

    public List<Member> getAssignedMembers() {
        return assignedMembers;
    }

    public void addAssignedMembers(Member assignedMember) {
        assignedMembers.add(assignedMember);
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void addLabels(Label label) {
        labels.add(label);
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void addAttachments(Attachment attachment) {
        attachments.add(attachment);
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public List<CheckList> getCheckLists() {
        return checkList;
    }

    public void addCheckLists(CheckList check) {
        checkList.add(check);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
