package spd.trello.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domian.Comment;
import spd.trello.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController extends AbstractResourceController<Comment, CommentService> {

    public CommentController(CommentService service) {
        super(service);
    }
}
