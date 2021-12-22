package spd.trello.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionToDB {

    public Connection getConnection() throws IOException {
        DataSource dataSource = createDataSource();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (connection != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Connection not established");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Properties loadProperties() throws IOException {
        InputStream in = ConnectionToDB.class.getClassLoader()
                .getResourceAsStream("application.properties");

        Properties properties = new Properties();
        properties.load(in);
        return properties;
    }

    public DataSource createDataSource() throws IOException {
        Properties properties = loadProperties();

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(properties.getProperty("jdbc.url"));
        cfg.setUsername(properties.getProperty("jdbc.username"));
        cfg.setPassword(properties.getProperty("jdbc.password"));

        int maxConnection = Integer.parseInt(properties.getProperty("jdbc.pool.maxConnection"));
        cfg.setMaximumPoolSize(maxConnection);

        return new HikariDataSource(cfg);
    }

}
