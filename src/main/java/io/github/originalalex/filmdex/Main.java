package io.github.originalalex.filmdex;

import io.github.originalalex.filmdex.server.Server;
import io.github.originalalex.filmdex.tmdb.data.API_INFORMATION;
import io.github.originalalex.filmdex.utils.io.PropertiesUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Properties;

public class Main {

    private static void initializeDefaults() {
        Properties props = PropertiesUtils.getProperties("/config.properties");
        API_INFORMATION.setKey(props.getProperty("API_KEY"));
    }


    private static void initializeServer() {
        Server.start();
    }

    public static void main(String[] args) {
        initializeDefaults();
        initializeServer();
        //WarmUp.warmUp();
    }

}
