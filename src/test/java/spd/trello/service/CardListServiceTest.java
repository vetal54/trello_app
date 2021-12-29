package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spd.trello.domain.CardList;
import spd.trello.repository.CardListRepositoryImpl;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CardListServiceTest extends BaseTest {

    private CardList cardList;
    private final CardListService service;

    public CardListServiceTest() {
        service = new CardListService(new CardListRepositoryImpl(dataSource));
    }

    @BeforeEach
    void create() {
        cardList = new CardList("cardListName");
        cardList.setBoard_id(UUID.fromString("5d9b85ab-d110-4e5d-a176-5ec2c716e3e7"));
    }

    @Test
    void createCardList() {
        assertNotNull(cardList);
        assertAll(
                () -> assertNotNull(cardList.getCreateDate()),
                () -> assertNull(cardList.getUpdateDate()),
                () -> assertEquals("cardListName", cardList.getName()),
                () -> assertEquals(Collections.emptyList() , cardList.getCards()),
                () -> assertTrue(cardList.getActive())
        );
    }

    @Test
    void printCardList() {
        assertEquals(cardList.getName() + ", id: " +  cardList.getId(), cardList.toString());
    }

    @Test
    void testSave() {
        service.repository.create(cardList);
        Optional<CardList> byId = service.findById(cardList.getId());
        assertNotNull(byId);
    }

    @Test
    void testFindById() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "CardList not found"
        );
        assertEquals("CardList with ID: " + uuid + " doesn't exists", ex.getMessage());
    }


    @Test
    void testDelete() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "CardList::findCardListById failed"
        );
        assertEquals("CardList with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(cardList);
        cardList.setName("it`s update cardList");
        service.update(cardList);
        Optional<CardList> startCardList = service.findById(cardList.getId());
        assertEquals("it`s update cardList", startCardList.get().getName());
    }
}