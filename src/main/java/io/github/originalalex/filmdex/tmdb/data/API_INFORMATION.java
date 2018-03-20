package io.github.originalalex.filmdex.tmdb.data;

public class API_INFORMATION {

    /*
    This class will contain the raw information for TMDB API which will primarily be used for URL_Building
     */

    private static String API_KEY;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static void setKey(String key) {
        API_KEY = key;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static String getBaseURL() {
        return BASE_URL;
    }

}
