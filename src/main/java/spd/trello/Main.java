package spd.trello;

import org.flywaydb.core.Flyway;
import spd.trello.repository.*;

import javax.sql.DataSource;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
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
