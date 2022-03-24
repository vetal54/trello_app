package spd.trello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spd.trello.domian.Card;
import spd.trello.service.CardService;

import java.util.UUID;

@RestController
@RequestMapping("/card")
public class CardController extends AbstractResourceController<Card, CardService> {

    public CardController(CardService service) {
        super(service);
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Card> update(@PathVariable UUID id, @RequestBody Card resource) {
        Card entity = service.findById(id);
        service.IsArchivedCard(entity);
        resource.setId(id);
        resource.setCreateDate(entity.getCreateDate());
        Card result = service.update(resource);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
