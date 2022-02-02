package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.CardList;
import spd.trello.repository.CardListRepositoryImpl;

import java.util.UUID;

@Service
public class CardListService extends AbstractService<CardList, CardListRepositoryImpl> {

    public CardListService(CardListRepositoryImpl repository) {
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
