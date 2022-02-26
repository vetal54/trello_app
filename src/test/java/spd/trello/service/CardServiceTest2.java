package spd.trello.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.domian.Board;
import spd.trello.domian.Card;
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
@Sql(statements = "DELETE FROM card")
class CardServiceTest2 {

    @Autowired
    private CardService service;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private CardListService cardListService;

    private CardList cardList;

    @BeforeEach
    void init() {
        Workspace workspace = workspaceService.create("name", "email", WorkspaceVisibility.PRIVATE, "description");
        Board board = boardService.create("name", "email", "description", BoardVisibility.PRIVATE, workspace.getId());
        cardList = cardListService.create("name", "email", board.getId());
    }

    @Test
    void cardWasSaved() {
        Card card = service.create("name", "email", "description", cardList.getId());
        Card cardSave = service.findById(card.getId());
        assertThat(cardSave).isEqualTo(card);
    }

    @Test
    void emptyListOfCardsIsReturned() {
        List<Card> cards = service.findAll();

        assertThat(cards).isEmpty();
    }

    @Test
    void notEmptyListOfCardsIsReturned() {
        Card card = service.create("name", "email", "description", cardList.getId());

        List<Card> cards = service.findAll();

        assertThat(cards).isNotEmpty();
    }

    @Test
    void cardWasNotFoundById() {
        assertThatCode(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void cardWasFoundById() {
        Card card = service.create("name", "email", "description", cardList.getId());
        Card cardFindById = service.findById(card.getId());

        assertThat(cardFindById).isEqualTo(card);
    }

    @Test
    void cardWasDeleted() {
        Card card = service.create("name", "email", "description", cardList.getId());

        service.delete(card.getId());

        assertThatCode(() -> service.findById(card.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void cardWasUpdated() {
        Card savedCard = service.create("name", "email", "description", cardList.getId());
        savedCard.setName("new Name");

        Card updatedCard = service.update(savedCard);

        assertThat(updatedCard.getName()).isEqualTo("new Name");
    }
}
