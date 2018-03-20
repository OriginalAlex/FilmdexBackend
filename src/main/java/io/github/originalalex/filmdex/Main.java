package io.github.originalalex.filmdex;

import io.github.originalalex.filmdex.tmdb.data.API_INFORMATION;
import io.github.originalalex.filmdex.utils.io.PropertiesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class Main {

    private static void initializeDefaults() {
        Properties props = PropertiesUtils.getProperties("/config.properties");
        API_INFORMATION.setKey(props.getProperty("API_KEY"));
    }

    private static void initializeServer() {
        SpringApplication.run(Main.class);
    }

    public static void main(String[] args) {
        //initializeDefaults();
        initializeServer();
    }

}
