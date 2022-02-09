package spd.trello.repository;

import org.springframework.stereotype.Repository;
import spd.trello.entity.resource.Comment;

@Repository
public interface CommentRepositoryImpl extends AbstractRepository<Comment> {
}
