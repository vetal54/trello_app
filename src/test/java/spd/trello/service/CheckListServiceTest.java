package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.CheckList;
import spd.trello.repository.CheckListRepositoryImpl;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CheckListServiceTest extends BaseTest {

    private CheckList checkList;
    private final CheckListService service;

    public CheckListServiceTest() {
        service = new CheckListService(new CheckListRepositoryImpl(dataSource));
    }

    @BeforeEach
    void create() {
        checkList = new CheckList("checkList");
    }

    @Test
    void createCheckList() {
        assertNotNull(checkList);
        assertAll(
                () -> assertNotNull(checkList.getCreateDate()),
                () -> assertNull(checkList.getUpdateDate()),
                () -> assertEquals("checkList", checkList.getName()),
                () -> assertEquals(Collections.emptyList(), checkList.getItems())
        );
    }

    @Test
    void printCheckList() {
        assertEquals(checkList.getName() + ", id: " + checkList.getId(), checkList.toString());
    }


    @Test
    void testSave() {
        service.repository.create(checkList);
        Optional<CheckList> byId = service.findById(checkList.getId());
        assertNotNull(byId);
    }

    @Test
    void testFindById() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.findById(uuid),
                "CheckList not found"
        );
        assertEquals("CheckList with ID: " + uuid + " doesn't exists", ex.getMessage());
    }


    @Test
    void testDelete() {
        UUID uuid = UUID.randomUUID();
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> service.delete(uuid),
                "CheckList::findCheckListById failed"
        );
        assertEquals("CheckList with ID: " + uuid + " doesn't exists", ex.getMessage());
    }

    @Test
    void testUpdate() {
        service.repository.create(checkList);
        checkList.setName("it`s update checkList");
        service.update(checkList);
        Optional<CheckList> startCardList = service.findById(checkList.getId());
        assertEquals("it`s update checkList", startCardList.get().getName());
    }
}