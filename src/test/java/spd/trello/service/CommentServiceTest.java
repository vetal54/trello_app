package spd.trello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import spd.trello.domian.Comment;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.CommentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository repository;
    private Comment comment;
    private CommentService service;

    void create() {
        comment = new Comment();
        comment.setContext("helli");
        comment.setCreateBy("@gmail");
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        service = new CommentService(repository);
        create();
    }

    @Test
    void commentSave() {
        when(repository.save(Mockito.any(Comment.class))).thenReturn(comment);
        Comment savedComment = repository.save(comment);
        assertThat(savedComment).isEqualTo(comment);
    }

    @Test
    void emptyListOfComments() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        List<Comment> comments = service.findAll();
        assertThat(comments).isEmpty();
    }

    @Test
    void oneElementOfListComments() {
        when(repository.findAll()).thenReturn(
                List.of(
                        comment
                )
        );
        List<Comment> comments = service.findAll();
        assertThat(comments).isEqualTo(List.of(comment));
    }

    @Test
    void commentWasNotFoundById() {
        when(repository.findById(comment.getId()))
                .thenReturn(Optional.empty());

        assertThatCode(() -> service.findById(comment.getId()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void commentWasFoundById() {
        when(repository.findById(comment.getId())).thenReturn(
                Optional.of(comment)
        );
        Comment commentFindById = service.findById(comment.getId());
        assertThat(commentFindById).isEqualTo(comment);
    }

    @Test
    void commentWasDeleted() {
        service.delete(comment.getId());
        verify(repository).deleteById(comment.getId());
    }

    @Test
    void commentWasUpdated() {
        when(repository.save(Mockito.any(Comment.class)))
                .thenReturn(comment);
        comment.setContext("new Name");
        Comment updatedComment = service.save(comment);
        assertThat(updatedComment.getContext()).isEqualTo(comment.getContext());
    }
}