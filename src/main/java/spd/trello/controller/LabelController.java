package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domian.Label;
import spd.trello.service.LabelService;

@RestController
@RequestMapping("/label")
public class LabelController extends AbstractDomainController<Label, LabelService> {

    public LabelController(LabelService service) {
        super(service);
    }
}
