package spd.trello;

import spd.trello.domain.*;
import spd.trello.service.BoardService;
import spd.trello.service.CardService;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CardService cardService = new CardService();
        BoardService boardService = new BoardService();
        Scanner scanner = new Scanner(System.in);
        boolean trueOrFalse = true;
        String input;
        while (trueOrFalse) {
            System.out.println("Enter the name of the object you want to create."
                    + "\nIt is can be \"board\" or \"card\". "
                    + "\nIf you don't want to create a new object enter \"-\".");
            input = scanner.next().toLowerCase(Locale.ROOT);
            switch (input) {
                case "board": {
                    Board board = boardService.create();
                    boardService.print(board);
                    break;
                }
                case "card": {
                    Card card = cardService.create();
                    cardService.print(card);
                    break;
                }
                case "-": {
                    trueOrFalse = false;
                    break;
                }
                default:
                    System.out.println("Your input is incorrect.\n");
            }
        }
    }
}
