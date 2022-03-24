package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domian.Comment;
import spd.trello.repository.CommentRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CommentService extends AbstractResourceService<Comment, CommentRepository> {

    public CommentService(CommentRepository repository) {
        super(repository);
    }

    public Comment create(String text, String email, UUID id) {
        Comment comment = new Comment();
        comment.setContext(text);
        comment.setCreateBy(email);
        comment.setCardId(id);
        return repository.save(comment);
    }
}
