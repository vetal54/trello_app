package spd.trello;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spd.trello.configuration.Config;
import spd.trello.configuration.FlywayMigrator;
import spd.trello.domain.*;
import spd.trello.service.*;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class, FlywayMigrator.class);

        WorkspaceService workspaceService = context.getBean(WorkspaceService.class);
        Workspace workspace = workspaceService.create(
                "workspace",
                "vet@gmail.com",
                WorkspaceVisibility.PRIVATE,
                "newWorkspace"
        );

        BoardService boardService = context.getBean(BoardService.class);
        Board board = boardService.create("board", "petro@gmail.com", workspace.getId());

        CardListService cardListService = context.getBean(CardListService.class);
        CardList cardList = cardListService.create("cardList", "frank@gmail.com", board.getId());

        CardService cardService = context.getBean(CardService.class);
        Card card = cardService.create("card", "tom@g,ail.com", "text", cardList.getId());

        CommentService commentService = context.getBean(CommentService.class);
        Comment comment = commentService.create("comment", "dog@gmail.com", card.getId());

        CheckListService checkListService = context.getBean(CheckListService.class);
        CheckList checkList = checkListService.create("checkList", "cat@gmail.com");

        ReminderService reminderService = context.getBean(ReminderService.class);
        Reminder reminder = reminderService.create(LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), card.getId());

        context.close();
    }
}
