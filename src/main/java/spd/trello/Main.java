package spd.trello;

import org.flywaydb.core.Flyway;
import spd.trello.domain.Board;
import spd.trello.domain.Card;
import spd.trello.domain.Workspace;
import spd.trello.repository.ConnectionToDB;
import spd.trello.service.*;
import javax.sql.DataSource;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        ConnectionToDB connection = new ConnectionToDB();
        DataSource dataSource = null;
        WorkspaceService ws = new WorkspaceService();
        CardService cs = new CardService();
        BoardService bs = new BoardService();

        Workspace workspace = ws.create();
        Board board = bs.create();
        workspace.addBoard(board);
        Card card = cs.create();

        try {
            dataSource = connection.createDataSource();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Flyway flyway = createFlyway(dataSource);
        flyway.migrate();

        WorkspaceDBActionsService wdbas = new WorkspaceDBActionsService(dataSource);
        wdbas.create(workspace);

        BoardDBActionsService bdbas = new BoardDBActionsService(dataSource);
        bdbas.create(board);

        DBActionsService dbas = new DBActionsService();
        dbas.getWorkspace(wdbas, "66953ccc-311f-4c9e-a99b-55f35f82ca90");
        dbas.updateWorkspace(wdbas, workspace);
        dbas.deleteWorkspace(wdbas, "7d7114c9-2518-4b37-964f-a09a1660a62f");
    }

    public static Flyway createFlyway(DataSource dataSource) {
        return Flyway.configure()
                .locations("classpath:db/migration")
                .dataSource(dataSource)
                .load();
    }
}
