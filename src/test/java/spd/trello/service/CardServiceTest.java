package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.Card;
import spd.trello.repository.CardRepositoryImpl;

import java.util.Collections;
import java.util.Optional;
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
        card = new Card("cardName");
        card.setDescription("New year 2022");
        card.setCardList_id(UUID.fromString("5ca1d56a-84e6-4245-a8dd-f4477da538bf"));
    }

    @Test
    void createCard() {
        assertNotNull(card);
        assertAll(
                () -> assertNotNull(card.getCreateDate()),
                () -> assertNull(card.getUpdateDate()),
                () -> assertEquals("cardName", card.getName()),
                () -> assertEquals("New year 2022", card.getDescription()),
                () -> assertEquals(Collections.emptyList() ,card.getComments()),
                () -> assertTrue(card.getActive())
        );
    }

    @Test
    void printCard() {
        assertEquals(card.getName() + ", id: " +  card.getId(), card.toString());
    }

    @Test
    void testSave() {
        service.repository.create(card);
        Optional<Card> byId = service.findById(card.getId());
        assertNotNull(byId);
    }

    @Test
    void testFindById() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "Card not found"
        );
        assertEquals("Card with ID: " + uuid + " doesn't exists", ex.getMessage());
    }


    @Test
    void testDelete() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "Card::findCardById failed"
        );
        assertEquals("Card with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(card);
        card.setName("it`s update card");
        service.update(card);
        Optional<Card> startCardList = service.findById(card.getId());
        assertEquals("it`s update card", startCardList.get().getName());
    }
}