package spd.trello.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spd.trello.domain.CheckList;
import spd.trello.repository.CheckListRepositoryImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CheckListServiceTest extends BaseTest {

    private static CheckList checkList;
    private final CheckListService service;

    public CheckListServiceTest() {
        service = new CheckListService(new CheckListRepositoryImpl(dataSource));
    }

    @BeforeAll
    static void create() {
        checkList = new CheckList("checkList");
    }

    @Test
    void printCheckList() {
        assertEquals(checkList.getName() + ", id: " + checkList.getId(), checkList.toString());
    }


    @Test
    void testSave() {
        service.repository.create(checkList);
        CheckList byId = service.findById(checkList.getId());
        assertEquals(checkList.getName(), byId.getName());
    }

    @Test
    void testFindById() {
        CheckList findCheckList = service.findById(checkList.getId());
        assertEquals(checkList.getName(), findCheckList.getName());
    }

    @Test
    void testFindByIdFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "CheckList not found"
        );
        assertEquals("CheckList with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(checkList);
        checkList.setName("it`s update checkList");
        service.update(checkList);
        CheckList startCardList = service.findById(checkList.getId());
        assertEquals(checkList.getName(), startCardList.getName());
    }

    @Test
    void testDelete() {
        boolean bool = service.delete(checkList.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "CheckList::findCheckListById failed"
        );
        assertEquals("CheckList with ID: " + uuid + " doesn't exists", ex.getMessage());
    }
}