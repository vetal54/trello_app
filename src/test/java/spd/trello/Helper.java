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
        User user = new User();
        user.setFirstName("string");
        user.setLastName("string");
        user.setEmail("string1@gmail.com");
        user.setPassword("admin");
        user.setRole(Role.ADMIN);
        return userService.save(user);
    }

    public Member createMember() {
        Member member = new Member();
        member.setRole(Role.ADMIN);
        member.setUserId(createUser().getId());
        return memberService.save(member);
    }

    public Workspace createWorkspace() {
        Workspace workspace = new Workspace();
        workspace.setName("string");
        workspace.setCreateBy("email@gmail.com");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspace.setDescription("description");
        return workspaceService.save(workspace);
    }

    public Board createBoard() {
        Board board = new Board();
        board.setName("string");
        board.setCreateBy("string@gmail.com");
        board.setDescription("description");
        board.setVisibility(BoardVisibility.PRIVATE);
        board.setWorkspaceId(createWorkspace().getId());
        return boardService.save(board);
    }

    public CardList createCardList() {
        CardList cardList = new CardList();
        cardList.setName("string");
        cardList.setCreateBy("string@gmail.com");
        cardList.setBoardId(createBoard().getId());
        return cardListService.save(cardList);
    }

    public Card createCard() {
        Card card = new Card();
        card.setName("string");
        card.setCreateBy("email@gmail.com");
        card.setDescription("description");
        card.setReminder(createReminder());
        card.setCheckList(createCheckList());
        card.setCardListId(createCardList().getId());
        return cardService.save(card);
    }

    public Attachment createAttachment() {
        Attachment attachment = new Attachment();
        attachment.setName("string");
        attachment.setLink("https://www.youtube.com/");
        attachment.setCreateBy("email@gmail.com");
        attachment.setCardId(createCard().getId());
        return attachmentService.save(attachment);
    }

    public Comment createComment() {
        Comment comment = new Comment();
        comment.setContext("context text! Hello!");
        comment.setCreateBy("email@gmail.com");
        comment.setCardId(createCard().getId());
       return commentService.save(comment);
    }

    public Label createLabel() {
        Label label = new Label();
        label.setName("string");
        label.setColor("color");
        label.setCardId( createCard().getId());
        return labelService.save(label);
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
