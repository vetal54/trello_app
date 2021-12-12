package spd.trello.service;

import spd.trello.domain.Board;
import spd.trello.domain.User;

public class BoardService extends AbstractService<Board> {
    @Override
    public Board create() {
        Board board = new Board();
        board.setName("board1");
        board.setCreateBy(new User());
        board.getCreateBy().setFirstName("Vitaliy");
        board.getCreateBy().setLastName("Dubovyk");
        board.getCreateBy().setEmail("vitaliy.dubovyk.1@gmail.com");
        return board;
    }

    @Override
    public void print(Board board) {
        System.out.println(board);
    }
}
