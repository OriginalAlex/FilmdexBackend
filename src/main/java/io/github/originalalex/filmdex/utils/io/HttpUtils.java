package io.github.originalalex.filmdex.utils.io;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

public class HttpUtils {

    private static final OkHttpClient client = new OkHttpClient();

    public static String performHTTPGet(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
