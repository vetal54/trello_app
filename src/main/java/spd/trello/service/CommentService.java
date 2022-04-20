package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Comment;
import spd.trello.repository.CommentRepository;

@Service
public class CommentService extends AbstractResourceService<Comment, CommentRepository> {

    public CommentService(CommentRepository repository) {
        super(repository);
    }
}
