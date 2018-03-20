package io.github.originalalex.filmdex.utils.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties getProperties(String relativePath) {
        try {
            System.out.println(relativePath);
            InputStream inputStream = PropertiesUtils.class.getResourceAsStream(relativePath);
            Properties props = new Properties();
            props.load(inputStream);
            return props;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
