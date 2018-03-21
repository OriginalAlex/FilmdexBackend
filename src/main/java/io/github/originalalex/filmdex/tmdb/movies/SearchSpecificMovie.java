package io.github.originalalex.filmdex.tmdb.movies;

import io.github.originalalex.filmdex.datastructures.cache.Cache;
import io.github.originalalex.filmdex.datastructures.cache.SimpleCache;
import io.github.originalalex.filmdex.tmdb.data.API_INFORMATION;
import io.github.originalalex.filmdex.utils.api.APIUtils;
import io.github.originalalex.filmdex.utils.io.HttpUtils;

public class SearchSpecificMovie {

    // URLS are of the form [base]/movie/[movie id]?api_key=[api_key]
    private static final Cache<String, String> cache = new SimpleCache<>();
    private static final String BASE_URL = API_INFORMATION.getBaseURL() + "/movie/";

    public static String getSpecificFilm(String query) {
        return APIUtils.fetchDataAndAddToCache(cache, (BASE_URL + query), query, "credits");
    }

}
