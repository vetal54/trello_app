package spd.trello.service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import spd.trello.repository.ConnectionToDB;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class BaseTest {

    protected static HikariDataSource dataSource;

    @BeforeAll
    public static void init() throws IOException {

        Properties properties = loadProperties();

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(properties.getProperty("jdbc.url"));
        cfg.setUsername(properties.getProperty("jdbc.username"));
        cfg.setPassword(properties.getProperty("jdbc.password"));
        cfg.setDriverClassName("org.postgresql.Driver");

        int maxConnection = Integer.parseInt(properties.getProperty("jdbc.pool.maxConnection"));
        cfg.setMaximumPoolSize(maxConnection);
        dataSource = new HikariDataSource(cfg);

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:migration")
                .load();
        flyway.migrate();
    }

    private static Properties loadProperties() throws IOException {
        InputStream in = ConnectionToDB.class.getClassLoader()
                .getResourceAsStream("testApplication.properties");

        Properties properties = new Properties();
        properties.load(in);
        return properties;
    }
}
