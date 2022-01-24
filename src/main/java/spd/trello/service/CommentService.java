package spd.trello.service;

import org.springframework.stereotype.Service;
import spd.trello.domain.Comment;
import spd.trello.repository.CommentRepositoryImpl;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CommentService extends ServiceLayer<Comment> {

    public CommentService(CommentRepositoryImpl repository) {
        super(repository);
    }

    public Comment create(String text, String email, UUID id) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setDate(LocalDateTime.now());
        comment.setCreateBy(email);
        comment.setCardId(id);
        print(comment);
        repository.create(comment);
        return repository.getById(comment.getId());
    }
}
