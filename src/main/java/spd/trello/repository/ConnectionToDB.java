package spd.trello.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionToDB {

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
