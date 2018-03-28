package io.github.originalalex.filmdex.utils.io;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONUtils {

    private static JsonParser parser = new JsonParser();

    public static JsonObject parseJSON(String json) {
        return parser.parse(json).getAsJsonObject();
    }

    public static String getProperty(JsonObject jsonObj, String propertyName) {
        return jsonObj.get(propertyName).getAsString();
    }

}
