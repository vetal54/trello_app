package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domian.Card;
import spd.trello.service.CardService;

@RestController
@RequestMapping("/card")
public class CardController extends AbstractResourceController<Card, CardService> {

    public CardController(CardService service) {
        super(service);
    }
}
