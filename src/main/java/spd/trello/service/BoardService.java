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
        board.setName(scanner.nextLine());
        System.out.println("Enter the email of the user who created the board:");
        board.setCreateBy(scanner.nextLine());
        print(board);
        return board;
    }

    @Override
    public void print(Board board) {
        System.out.println(board);
    }
}
