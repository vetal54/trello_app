package spd.trello.controller;

import org.springframework.web.bind.annotation.*;
import spd.trello.domian.Attachment;
import spd.trello.service.AttachmentService;

@RestController
@RequestMapping("/attachment")
public class AttachmentController extends AbstractResourceController<Attachment, AttachmentService> {

    public AttachmentController(AttachmentService service) {
        super(service);
    }
}
