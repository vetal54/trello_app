package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domian.CardList;
import spd.trello.service.CardListService;

@RestController
@RequestMapping("/card-list")
public class CardListController extends AbstractResourceController<CardList, CardListService> {

    public CardListController(CardListService service) {
        super(service);
    }
}
