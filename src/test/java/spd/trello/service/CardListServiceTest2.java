package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.domian.Board;
import spd.trello.domian.CardList;
import spd.trello.domian.Workspace;
import spd.trello.domian.type.BoardVisibility;
import spd.trello.domian.type.WorkspaceVisibility;
import spd.trello.exeption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM card_list")
class CardListServiceTest2 {

    @Autowired
    private CardListService service;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private BoardService boardService;

    private Board board;

    @BeforeEach
    void init() {
        Workspace workspace = workspaceService.create("name", "email", WorkspaceVisibility.PRIVATE, "description");
        board = boardService.create("name", "email", "description", BoardVisibility.PRIVATE, workspace.getId());
    }

    @Test
    void cardListWasSaved() {
        CardList cardList = service.create("name", "email", board.getId());
        CardList cardListSave = service.findById(cardList.getId());
        assertThat(cardListSave).isEqualTo(cardList);
    }

    @Test
    void emptyListOfCardListsIsReturned() {
        List<CardList> cardLists = service.findAll();

        assertThat(cardLists).isEmpty();
    }

    @Test
    void notEmptyListOfCardListsIsReturned() {
        CardList cardList = service.create("name", "email", board.getId());

        List<CardList> cardLists = service.findAll();

        assertThat(cardLists).isNotEmpty();
    }

    @Test
    void cardListWasNotFoundById() {
        assertThatCode(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void cardListWasFoundById() {
        CardList cardList = service.create("name", "email", board.getId());
        CardList cardListFindById = service.findById(cardList.getId());

        assertThat(cardListFindById).isEqualTo(cardList);
    }

    @Test
    void cardListWasDeleted() {
        CardList cardList = service.create("name", "email", board.getId());

        service.delete(cardList.getId());

        assertThatCode(() -> service.findById(cardList.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void cardListWasUpdated() {
        CardList savedCardList = service.create("name", "email", board.getId());
        savedCardList.setName("new Name");

        CardList updatedCardList = service.update(savedCardList);

        assertThat(updatedCardList.getName()).isEqualTo("new Name");
    }
}
