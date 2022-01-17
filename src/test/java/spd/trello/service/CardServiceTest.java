package spd.trello.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Card;
import spd.trello.repository.CardRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CardServiceTest extends BaseTest {

    private static Card card;
    private final CardService service;

    public CardServiceTest() {
        service = new CardService(new CardRepositoryImpl(dataSource));
    }

    @BeforeAll
    static void create() {
        card = new Card("cardName");
        card.setDescription("New year 2022");
        card.setCardListId(UUID.fromString("5ca1d56a-84e6-4245-a8dd-f4477da538bf"));
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
        Card findCard = service.findById(card.getId());
        assertEquals(card.getName(), findCard.getName());
    }

    @Test
    void testFindByIdFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "Card not found"
        );
        assertEquals("Card with ID: " + uuid + " doesn't exists", ex.getMessage());
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
    void testDelete() {
        boolean bool = service.delete(card.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "Card::findCardById failed"
        );
        assertEquals("Card with ID: " + uuid + " doesn't exists", ex.getMessage());
    }
}