package spd.trello.service;

class CheckListServiceTest extends BaseTest {

//    private CheckList checkList;
//    private final CheckListService service;
//
//    public CheckListServiceTest() {
//        service = new CheckListService(new CheckListRepositoryImpl(jdbcTemplate));
//    }
//
//    @BeforeEach
//    void create() {
//        checkList = new CheckList();
//        checkList.setName("checkList");
//    }
//
//    @Test
//    void printCheckList() {
//        assertEquals(checkList.getName() + ", id: " + checkList.getId(), checkList.toString());
//    }
//
//
//    @Test
//    void testSave() {
//        service.repository.save(checkList);
//        CheckList byId = service.findById(checkList.getId());
//        assertEquals(checkList.getName(), byId.getName());
//    }
//
//    @Test
//    void testFindById() {
//        service.repository.save(checkList);
//        CheckList findCheckList = service.findById(checkList.getId());
//        assertEquals(checkList.getName(), findCheckList.getName());
//    }
//
//    @Test
//    void testUpdate() {
//        service.repository.save(checkList);
//        checkList.setName("it`s update checkList");
//        service.update(checkList);
//        CheckList startCardList = service.findById(checkList.getId());
//        assertEquals(checkList.getName(), startCardList.getName());
//    }
//
//    @Test
//    void testFindAll() {
//        service.repository.save(checkList);
//        service.create("checkList", "v@gmail.com");
//        service.create("checkList2", "d@gmail.com");
//        assertEquals(3, service.findAll().size());
//    }
//
//    @Test
//    void testDelete() {
//        service.repository.save(checkList);
//        boolean bool = service.delete(checkList.getId());
//        assertTrue(bool);
//    }
//
//    @Test
//    void testDeleteFailed() {
//        UUID uuid = UUID.randomUUID();
//        assertFalse(service.delete(uuid));
//    }
}