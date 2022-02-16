package spd.trello.service;

class CardServiceTest {

//    private Card card;
//    private final CardService service;
//
//    public CardServiceTest() {
//        service = new CardService(new CardRepository(jdbcTemplate));
//    }
//
//    @BeforeEach
//    void create() {
//        CardList cardList = new CardList( );
//        CardListService cls = new CardListService(new CardListRepository(jdbcTemplate));
//        cls.repository.save(cardList);
//        card = new Card( );
//        card.setDescription("New year 2022");
//        card.setCardListId(cardList.getId());
//    }
//
//    @Test
//    void printCard() {
//        assertEquals(card.getName() + ", id: " + card.getId(), card.toString());
//    }
//
//    @Test
//    void testSave() {
//        service.repository.save(card);
//        Card byId = service.findById(card.getId());
//        assertEquals(card.getName(), byId.getName());
//    }
//
//    @Test
//    void testFindById() {
//        service.repository.save(card);
//        Card findCard = service.findById(card.getId());
//        assertEquals(card.getName(), findCard.getName());
//    }
//
//    @Test
//    void testUpdate() {
//        service.repository.save(card);
//        card.setName("it`s update card");
//        service.update(card);
//        Card startCardList = service.findById(card.getId());
//        assertEquals(card.getName(), startCardList.getName());
//    }
//
//    @Test
//    void testFindAll() {
//        service.repository.save(card);
//        service.create("card", "v@gmail.com", "Hi!", card.getCardListId());
//        service.create("card2", "d@gmail.com", "Hi?", card.getCardListId());
//        assertEquals(3, service.findAll().size());
//    }
//
//    @Test
//    void testDelete() {
//        service.repository.save(card);
//        boolean bool = service.delete(card.getId());
//        assertTrue(bool);
//    }
//
//    @Test
//    void testDeleteFailed() {
//        UUID uuid = UUID.randomUUID();
//        assertFalse(service.delete(uuid));
//    }
}