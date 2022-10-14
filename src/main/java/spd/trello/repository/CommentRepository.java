package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.domian.Comment;

@Repository
public interface CommentRepository extends AbstractRepository<Comment> {
}
