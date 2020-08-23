package io.github.originalalex.filmdex;

import io.github.originalalex.filmdex.server.Server;
import io.github.originalalex.filmdex.tmdb.data.API_INFORMATION;
import io.github.originalalex.filmdex.utils.io.PropertiesUtils;
import java.util.Properties;

public class Main {

    private static void initializeDefaults() {
        Properties props = PropertiesUtils.getProperties("/defaultConfig.properties");
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
