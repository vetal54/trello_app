package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.domain.User;

import java.util.Scanner;

public class BoardService extends AbstractService<Board> {
    @Override
    public Board create() {
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter board name:");
        board.setName(scanner.next());
        System.out.println("Enter information about the user who created the board");
        board.setCreateBy(new User());
        System.out.println("Enter first name:");
        board.getCreateBy().setFirstName(scanner.next());
        System.out.println("Enter last name");
        board.getCreateBy().setLastName(scanner.next());
        System.out.println("Enter user email");
        board.getCreateBy().setEmail(scanner.next());
        return board;
    }

    @Override
    public void print(Board board) {
        System.out.println(board);
    }
}
