package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spd.trello.domain.CheckList;
import spd.trello.repository.CheckListRepositoryImpl;

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
        service.repository.create(checkList);
        CheckList findCheckList = service.findById(checkList.getId());
        assertEquals(checkList.getName(), findCheckList.getName());
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
    void testFindAll() {
        service.repository.create(checkList);
        service.create("checkList", "v@gmail.com");
        service.create("checkList2", "d@gmail.com");
        assertEquals(3, service.findAll().size());
    }

    @Test
    void testDelete() {
        service.repository.create(checkList);
        boolean bool = service.delete(checkList.getId());
        assertTrue(bool);
    }

    @Test
    void testDeleteFailed() {
        UUID uuid = UUID.randomUUID();
        assertFalse(service.delete(uuid));
    }
}