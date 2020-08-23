package io.github.originalalex.filmdex.tmdb.movies;

import io.github.originalalex.filmdex.datastructures.cache.SingleItemCache;
import io.github.originalalex.filmdex.tmdb.data.API_INFORMATION;
import io.github.originalalex.filmdex.utils.api.APIUtils;
import io.github.originalalex.filmdex.utils.io.HttpUtils;

public class MoviesSearch {

    /**
     * This class provides the information needed to generate the homepage for the website
     * This includes:
     * - Upcoming films
     * - Recent releases
     */

    private static final SingleItemCache<String> cache = new SingleItemCache<>();
    private static final String BASE_URL = API_INFORMATION.getBaseURL() + "/movie";

    public static String getHomepageInformation() {
        if (cache.needsRefresh()) {
            String upcomingURL =  BASE_URL + "/upcoming?api_key=" + API_INFORMATION.getApiKey();
            String popularReleasesURL = BASE_URL + "/popular?api_key=" + API_INFORMATION.getApiKey();
            String upcomingResponse = HttpUtils.performHTTPGet(upcomingURL);
            String popularResponse = HttpUtils.performHTTPGet(popularReleasesURL);
            cache.setValue("{\"popularReleases\": " + popularResponse + ", \"upcomingReleases\": " + upcomingResponse + "}");
        }
        return cache.getValue();
    }


}
