package spd.trello;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import spd.trello.domian.*;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.domian.type.Role;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.service.*;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

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
        user.setEmail("admin@gmail.com");
        user.setPassword("admin");
        return userService.register(user);
    }

    public Member createMember(User user) {
        Member member = new Member();
        member.setRole(Role.ADMIN);
        member.setUserId(user.getId());
        return memberService.save(member);
    }

    public Workspace createWorkspace() {
        Workspace workspace = new Workspace();
        workspace.setName("string");
        workspace.setCreateBy("admin@gmail.com");
        workspace.setVisibility(WorkspaceVisibility.PRIVATE);
        workspace.setDescription("description");
        return workspaceService.save(workspace);
    }

    public Board createBoard() {
        Board board = new Board();
        board.setName("string");
        board.setCreateBy("admin@gmail.com");
        board.setDescription("description");
        board.setVisibility(BoardVisibility.PRIVATE);
        board.setWorkspaceId(createWorkspace().getId());
        return boardService.save(board);
    }

    public CardList createCardList() {
        CardList cardList = new CardList();
        cardList.setName("string");
        cardList.setCreateBy("admin@gmail.com");
        cardList.setBoardId(createBoard().getId());
        return cardListService.save(cardList);
    }

    public Card createCard() {
        Card card = new Card();
        card.setName("string");
        card.setDescription("description");
        card.setCreateBy("admin@gmail.com");
        card.setReminder(createReminder());
        card.setCheckList(createCheckList());
        card.setCardListId(createCardList().getId());
        return cardService.save(card);
    }

    public Attachment createAttachment() {
        Attachment attachment = new Attachment();
        attachment.setName("string");
        attachment.setLink("https://www.youtube.com/");
        attachment.setCreateBy("admin@gmail.com");
        attachment.setCardId(createCard().getId());
        return attachmentService.save(attachment);
    }

    public Comment createComment() {
        Comment comment = new Comment();
        comment.setCreateBy("admin@gmail.com");
        comment.setContext("context text! Hello!");
        comment.setCardId(createCard().getId());
        return commentService.save(comment);
    }

    public Label createLabel() {
        Label label = new Label();
        label.setName("string");
        label.setColor("color");
        label.setCardId(createCard().getId());
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

    public String createUserAdminAndGetToken(User user) {
        AuthenticationRequestDTO authentication = new AuthenticationRequestDTO();
        authentication.setEmail(user.getEmail());
        authentication.setPassword("admin");
        Map<Object, Object> result = userService.authenticate(authentication);
        String token = result.toString();
        if (token != null) {
            token = token.replace("{email=admin@gmail.com, token=", "");
            token = token.replace("}", "");
        }
        return token;
    }

    public List<User> getUsersArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );
    }

    public List<Member> getMembersArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );
    }

    public List<Workspace> getWorkspacesArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );
    }

    public List<Board> getBoardsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );
    }

    public List<CardList> getCardListsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );
    }

    public List<Card> getCardsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );
    }

    public List<Comment> getCommentsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );
    }

    public List<Attachment> getAttachmentsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );
    }

    public List<Label> getLabelsArray(MvcResult mvcResult) throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
                }
        );
    }
}
