package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.CardList;
import spd.trello.exeption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM card_list")
class CardListServiceTest {

    @Autowired
    private CardListService service;
    @Autowired
    private Helper helper;

    @Test
    void cardListWasSaved() {
        CardList cardList = helper.createCardList();
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
        CardList cardList = helper.createCardList();

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
        CardList cardList = helper.createCardList();
        CardList cardListFindById = service.findById(cardList.getId());

        assertThat(cardListFindById).isEqualTo(cardList);
    }

    @Test
    void cardListWasDeleted() {
        CardList cardList = helper.createCardList();

        service.delete(cardList.getId());

        assertThatCode(() -> service.findById(cardList.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void cardListWasUpdated() {
        CardList savedCardList = helper.createCardList();
        savedCardList.setName("new Name");

        CardList updatedCardList = service.update(savedCardList);

        assertThat(updatedCardList.getName()).isEqualTo("new Name");
    }
}
