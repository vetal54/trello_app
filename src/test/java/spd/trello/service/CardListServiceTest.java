package spd.trello.service;

class CardListServiceTest {

//    private CardList cardList;
//    private final CardListService service;
//
//    public CardListServiceTest() {
//        service = new CardListService(new CardListRepository(jdbcTemplate));
//    }
//
//    @BeforeEach
//    void create() {
//        Board board = new Board();
//        BoardService bs = new BoardService(new BoardRepository(jdbcTemplate));
//        bs.repository.save(board);
//        cardList = new CardList();
//        cardList.setBoardId(board.getId());
//    }
//
//    @Test
//    void printCardList() {
//        assertEquals(cardList.getName() + ", id: " + cardList.getId(), cardList.toString());
//    }
//
//    @Test
//    void testSave() {
//        service.repository.save(cardList);
//        CardList byId = service.findById(cardList.getId());
//        assertNotNull(byId);
//
//        assertAll(
//                () -> assertEquals(cardList.getName(), byId.getName()),
//                () -> assertEquals(cardList.getBoardId(), byId.getBoardId())
//        );
//    }
//
//    @Test
//    void testFindById() {
//        service.repository.save(cardList);
//        CardList findCardList = service.findById(cardList.getId());
//        assertEquals(findCardList.getName(), cardList.getName());
//    }
//
//    @Test
//    void testUpdate() {
//        service.repository.save(cardList);
//        cardList.setName("it`s update cardList");
//        service.update(cardList);
//        CardList startCardList = service.findById(cardList.getId());
//        assertEquals(cardList.getName(), startCardList.getName());
//    }
//
//    @Test
//    void testFindAll() {
//        service.repository.save(cardList);
//        service.create("cardList", "v@gmail.com", cardList.getBoardId());
//        service.create("cardList2", "d@gmail.com", cardList.getBoardId());
//        assertEquals(3, service.findAll().size());
//    }
//
//    @Test
//    void testDelete() {
//        service.repository.save(cardList);
//        boolean bool = service.delete(cardList.getId());
//        assertTrue(bool);
//    }
//
//    @Test
//    void testDeleteFailed() {
//        UUID uuid = UUID.randomUUID();
//        assertFalse(service.delete(uuid));
//    }
}