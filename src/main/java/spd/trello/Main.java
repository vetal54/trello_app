package spd.trello;

import spd.trello.domain.*;
import spd.trello.service.BoardService;
import spd.trello.service.CardService;

public class Main {
    public static void main(String[] args) {
        CardService cardService = new CardService();
        Card card = cardService.create();
        cardService.print(card);

        BoardService boardService = new BoardService();
        Board board = boardService.create();
        boardService.print(board);
    }
}
