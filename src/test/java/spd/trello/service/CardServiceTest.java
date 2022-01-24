package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Card;
import spd.trello.domain.CardList;
import spd.trello.repository.CardListRepositoryImpl;
import spd.trello.repository.CardRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CardServiceTest extends BaseTest {

    private Card card;
    private final CardService service;

    public CardServiceTest() {
        service = new CardService(new CardRepositoryImpl(dataSource));
    }

    @BeforeEach
    void create() {
        CardList cardList = new CardList("new CardList");
        CardListService cls = new CardListService(new CardListRepositoryImpl(dataSource));
        cls.repository.create(cardList);
        card = new Card("cardName");
        card.setDescription("New year 2022");
        card.setCardListId(cardList.getId());
    }

    @Test
    void printCard() {
        assertEquals(card.getName() + ", id: " + card.getId(), card.toString());
    }

    @Test
    void testSave() {
        service.repository.create(card);
        Card byId = service.findById(card.getId());
        assertEquals(card.getName(), byId.getName());
    }

    @Test
    void testFindById() {
        service.repository.create(card);
        Card findCard = service.findById(card.getId());
        assertEquals(card.getName(), findCard.getName());
    }

    @Test
    void testUpdate() {
        service.repository.create(card);
        card.setName("it`s update card");
        service.update(card);
        Card startCardList = service.findById(card.getId());
        assertEquals(card.getName(), startCardList.getName());
    }

    @Test
    void testFindAll() {
        service.repository.create(card);
        service.create("card", "v@gmail.com", "Hi!", card.getCardListId());
        service.create("card2", "d@gmail.com", "Hi?", card.getCardListId());
        assertEquals(3, service.findAll().size());
    }

    @Test
    void testDelete() {
        service.repository.create(card);
        boolean bool = service.delete(card.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        assertFalse(service.delete(uuid));
    }
}