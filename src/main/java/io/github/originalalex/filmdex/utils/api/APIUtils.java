package io.github.originalalex.filmdex.utils.api;

import io.github.originalalex.filmdex.datastructures.cache.Cache;
import io.github.originalalex.filmdex.datastructures.cache.SingleItemCache;
import io.github.originalalex.filmdex.tmdb.data.API_INFORMATION;
import io.github.originalalex.filmdex.utils.io.HttpUtils;

public class APIUtils {

    private static String buildUrl(String url, String[] append) {
        if (url.contains("?")) url += "&api_key=" + API_INFORMATION.getApiKey();
        else url += "?api_key=" + API_INFORMATION.getApiKey();
        if (append.length != 0) {
            url += "&append_to_response=" + append[0];
            for (int i = 1; i < append.length; i++) {
                url += "," + append[i];
            }
        }
        return url;
    }

    public static String fetchDataAndAddToCache(Cache<String, String> cache, String url, String query, String... append) {
        url = buildUrl(url, append);
        if (!cache.contains(query) || cache.needsRefresh(query)) {
            String response = HttpUtils.performHTTPGet(url);
            cache.put(query, response);
            return response;
        }
        return cache.get(query);
    }

    public static String fetchDataAndAdToSingleCache(SingleItemCache<String> cache, String url, String... append) {
        url = buildUrl(url, append);
        System.out.println(url);
        if (cache.needsRefresh()) {
            String response = HttpUtils.performHTTPGet(url);
            cache.setValue(response);
            return response;
        }
        return cache.getValue();
    }

}
