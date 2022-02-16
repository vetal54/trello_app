package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.CardList;
import spd.trello.repository.CardListRepository;

import java.util.UUID;

@Service
public class CardListService extends AbstractService<CardList, CardListRepository> {

    public CardListService(CardListRepository repository) {
        super(repository);
    }

    public CardList create(String name, String email, UUID id) {
        CardList cardList = new CardList();
        cardList.setName(name);
        cardList.setCreateBy(email);
        cardList.setBoardId(id);
        repository.save(cardList);
        return repository.getById(cardList.getId());
    }
}
