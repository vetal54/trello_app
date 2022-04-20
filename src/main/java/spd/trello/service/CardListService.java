package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.CardList;
import spd.trello.repository.CardListRepository;

@Service
public class CardListService extends AbstractResourceService<CardList, CardListRepository> {

    public CardListService(CardListRepository repository) {
        super(repository);
    }
}
