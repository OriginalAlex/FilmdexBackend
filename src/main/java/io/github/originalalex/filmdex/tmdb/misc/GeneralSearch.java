package io.github.originalalex.filmdex.tmdb.misc;

import io.github.originalalex.filmdex.datastructures.cache.Cache;
import io.github.originalalex.filmdex.datastructures.cache.SimpleCache;
import io.github.originalalex.filmdex.tmdb.data.API_INFORMATION;
import io.github.originalalex.filmdex.utils.api.APIUtils;

public class GeneralSearch {

    private static final Cache<String, String> cache = new SimpleCache<>();
    private static final String BASE_URL = API_INFORMATION.getBaseURL() + "/search/multi";

    public static String search(String query) {
        return APIUtils.fetchDataAndAddToCache(cache, (BASE_URL + "?query=" + query), query);
    }

}
