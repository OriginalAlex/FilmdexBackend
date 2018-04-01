package io.github.originalalex.filmdex.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Server {

    public static void start() {
        SpringApplication.run(Server.class);
    }

}
