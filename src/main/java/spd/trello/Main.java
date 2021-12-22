package spd.trello;

import org.flywaydb.core.Flyway;
import spd.trello.repository.ConnectionToDB;
import spd.trello.service.BoardService;
import spd.trello.service.CardService;

import javax.sql.DataSource;
import java.io.IOException;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        CardService cardService = new CardService();
        BoardService boardService = new BoardService();
        Scanner scanner = new Scanner(in);
        boolean trueOrFalse = true;
        String input;
        while (trueOrFalse) {
            out.println("""
                    Enter the name of the object you want to create.
                    It is can be "board" or "card".\s
                    If you don't want to create a new object enter "-".""");
            input = scanner.next().toLowerCase(Locale.ROOT);
            switch (input) {
                case "board" -> boardService.print(boardService.create());
                case "card" -> cardService.print(cardService.create());
                case "-" -> trueOrFalse = false;
                default -> out.println("Your input is incorrect.\n");
            }
        }
        ConnectionToDB connection = new ConnectionToDB();
        DataSource dataSource = null;
        try {
            dataSource = connection.createDataSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Flyway flyway = createFlyway(dataSource);
        flyway.migrate();
    }

    public static Flyway createFlyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .load();
    }
}
