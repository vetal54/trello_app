package spd.trello.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import spd.trello.Helper;
import spd.trello.domian.Comment;
import spd.trello.exeption.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Sql(statements = "DELETE FROM comment")
class CommentServiceTest {

    @Autowired
    private CommentService service;
    @Autowired
    private Helper helper;

    @Test
    void commentWasSaved() {
        Comment comment = helper.createComment();
        Comment commentSave = service.findById(comment.getId());
        assertThat(commentSave).isEqualTo(comment);
    }

    @Test
    void emptyListOfCommentsIsReturned() {
        List<Comment> comments = service.findAll();

        assertThat(comments).isEmpty();
    }

    @Test
    void notEmptyListOfCommentsIsReturned() {
        Comment comment = helper.createComment();

        List<Comment> comments = service.findAll();

        assertThat(comments).isNotEmpty();
    }

    @Test
    void commentWasNotFoundById() {
        assertThatCode(() -> service.findById(UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void commentWasFoundById() {
        Comment comment = helper.createComment();
        Comment commentFindById = service.findById(comment.getId());

        assertThat(commentFindById).isEqualTo(comment);
    }

    @Test
    void commentWasDeleted() {
        Comment comment = helper.createComment();

        service.delete(comment.getId());

        assertThatCode(() -> service.findById(comment.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void commentWasUpdate() {
        Comment savedComment = helper.createComment();
        savedComment.setContext("new Context");

        Comment updatedComment = service.update(savedComment);

        assertThat(updatedComment.getContext()).isEqualTo("new Context");
    }
}