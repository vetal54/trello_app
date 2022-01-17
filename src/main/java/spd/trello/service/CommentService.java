package spd.trello.service;

import spd.trello.domain.Comment;
import spd.trello.repository.CommentRepositoryImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CommentService extends AbstractService<Comment> {

    public CommentService(CommentRepositoryImpl repository) {
        super(repository);
    }

    @Override
    public Comment create() {
        Comment comment = new Comment();
        System.out.println("Enter text of comment");
        Scanner scanner = new Scanner(System.in);
        comment.setText(scanner.nextLine());
        comment.setDate(LocalDateTime.now());
        print(comment);
        repository.create(comment);
        return comment;
    }

    @Override
    public void print(Comment comment) {
        System.out.println(comment);
    }

    @Override
    public void update(Comment comment) {
        repository.update(comment);
    }

    @Override
    public Comment findById(UUID id) {
        return repository.getById(id);
    }

    @Override
    public List<Comment> findAll() {
        return repository.getAll();
    }

    @Override
    boolean delete(UUID id) {
        return repository.delete(id);
    }
}
