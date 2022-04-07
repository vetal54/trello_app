package spd.trello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spd.trello.domian.*;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.domian.type.Role;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.service.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class Helper {
    @Autowired
    private UserService userService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private WorkspaceService workspaceService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private CardListService cardListService;
    @Autowired
    private CardService cardService;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LabelService labelService;

    public User createUser() {
        return userService.create(
                "string",
                "string",
                "string1@gmail.com"
        );
    }

    public Member createMember() {
        return memberService.create(
                Role.ADMIN,
                createUser().getId()
        );
    }

    public Workspace createWorkspace() {
        return workspaceService.create(
                "string",
                "email@gmail.com",
                WorkspaceVisibility.PRIVATE,
                "description"
        );
    }

    public Board createBoard() {
        return boardService.create(
                "string",
                "string@gmail.com",
                "description",
                BoardVisibility.PRIVATE,
                createWorkspace().getId()
        );
    }

    public CardList createCardList() {
        return cardListService.create(
                "string",
                "string@gmail.com",
                createBoard().getId()
        );
    }

    public Card createCard() {
        return cardService.create(
                "string",
                "email@gmail.com",
                "description",
                createReminder(),
                createCheckList(),
                createCardList().getId()
        );
    }

    public Attachment createAttachment() {
        return attachmentService.createAttachment(
                "string",
                "email@gmail.com",
                "https://www.youtube.com/",
                createCard().getId()
        );
    }

    public Comment createComment() {
        return commentService.create(
                "context text! Hello!",
                "email@gmail.com",
                createCard().getId()
        );
    }

    public Label createLabel() {
        return labelService.create(
                "string",
                "color",
                createCard().getId()
        );
    }

    public Reminder createReminder() {
        Reminder reminder = new Reminder();
        reminder.setStart(LocalDateTime.now().plus(Duration.of(5, ChronoUnit.MINUTES)));
        reminder.setRemindOn(LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES)));
        reminder.setEnd(LocalDateTime.now().plus(Duration.of(15, ChronoUnit.MINUTES)));
        return reminder;
    }

    public CheckList createCheckList() {
        CheckList checkList = new CheckList();
        checkList.setName("string");
        Item item = new Item();
        item.setName("string");
        checkList.getItems().add(item);
        return checkList;
    }
}
