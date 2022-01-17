package spd.trello.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import spd.trello.domain.Board;
import spd.trello.domain.BoardVisibility;
import spd.trello.domain.CardList;
import spd.trello.repository.BoardRepositoryImpl;
import spd.trello.repository.CardListRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CardListServiceTest extends BaseTest {

    private static CardList cardList;
    private final CardListService service;

    public CardListServiceTest() {
        service = new CardListService(new CardListRepositoryImpl(dataSource));
    }

    @BeforeAll
    static void create() {
        Board board = new Board("board", "new Board", BoardVisibility.PRIVATE);
        BoardService bs = new BoardService(new BoardRepositoryImpl(dataSource));
        bs.repository.create(board);
        cardList = new CardList("cardListName");
        cardList.setBoardId(board.getId());
    }

    @Test
    void printCardList() {
        assertEquals(cardList.getName() + ", id: " + cardList.getId(), cardList.toString());
    }

    @Test
    void testSave() {
        service.repository.create(cardList);
        CardList byId = service.findById(cardList.getId());
        assertNotNull(byId);

        assertAll(
                () -> assertEquals(cardList.getName(), byId.getName()),
                () -> assertEquals(cardList.getBoardId(), byId.getBoardId())
        );
    }

    @Test
    void testFindById() {
        CardList findCardList = service.findById(cardList.getId());
        assertEquals(findCardList.getName(), cardList.getName());
    }

    @Test
    void testFindByIdFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "CardList not found"
        );
        assertEquals("CardList with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(cardList);
        cardList.setName("it`s update cardList");
        service.update(cardList);
        CardList startCardList = service.findById(cardList.getId());
        assertEquals(cardList.getName(), startCardList.getName());
    }

    @Test
    void testDelete() {
        boolean bool = service.delete(cardList.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "CardList::findCardListById failed"
        );
        assertEquals("CardList with ID: " + uuid + " doesn't exists", ex.getMessage());
    }
}