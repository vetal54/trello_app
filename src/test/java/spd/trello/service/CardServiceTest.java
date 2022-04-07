package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.Card;
import spd.trello.exeption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM card")
class CardServiceTest {

    @Autowired
    private CardService service;
    @Autowired
    private Helper helper;

    @Test
    void cardWasSaved() {
        Card card = helper.createCard();
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
        Card card = helper.createCard();

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
        Card card = helper.createCard();
        Card cardFindById = service.findById(card.getId());

        assertThat(cardFindById).isEqualTo(card);
    }

    @Test
    void cardWasDeleted() {
        Card card = helper.createCard();

        service.delete(card.getId());

        assertThatCode(() -> service.findById(card.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void cardWasUpdated() {
        Card savedCard = helper.createCard();
        savedCard.setName("new Name");

        Card updatedCard = service.update(savedCard);

        assertThat(updatedCard.getName()).isEqualTo("new Name");
    }
}
