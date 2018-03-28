package io.github.originalalex.filmdex;

import io.github.originalalex.filmdex.database.DatabaseModifier;
import io.github.originalalex.filmdex.tmdb.data.API_INFORMATION;
import io.github.originalalex.filmdex.utils.io.PropertiesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class Main {

    private static void initializeDefaults() {
        Properties props = PropertiesUtils.getProperties("/config.properties");
        initializeDatabase(props);
        API_INFORMATION.setKey(props.getProperty("API_KEY"));
    }

    private static void initializeDatabase(Properties props) {
        DatabaseModifier modifier = new DatabaseModifier(
                props.getProperty("Database_host"),
                props.getProperty("Database_port"),
                props.getProperty("Username"),
                props.getProperty("Password"),
                props.getProperty("Database_name")
        );
    }

    private static void initializeServer() {
        SpringApplication.run(Main.class);
    }

    public static void main(String[] args) {
        initializeDefaults();
        //initializeServer();
        //WarmUp.warmUp();
    }

}
