package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.repository.BoardRepositoryImpl;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class BoardService extends AbstractService<Board> {

    public BoardService(BoardRepositoryImpl repository) {
        super(repository);
    }

    @Override
    public Board create() {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter board name:");
        board.setName(scanner.nextLine());
        System.out.println("Enter the email of the user who created the board:");
        board.setCreateBy(scanner.nextLine());
        print(board);
        repository.create(board);
        return board;
                //repository.getById(board.getId());
    }

    @Override
    public void print(Board board) {
        System.out.println(board);
    }

    @Override
    public void update(Board board) {
        repository.update(board);
    }

    @Override
    public Board findById(UUID id) {
        return repository.getById(id);
    }

    @Override
    public List<Board> findAll() {
       return repository.getAll();
    }

    @Override
    public boolean delete(UUID id) {
        return repository.delete(id);
    }
}
