package io.github.originalalex.filmdex.utils.io;

import io.github.originalalex.filmdex.tmdb.misc.GeneralSearch;
import io.github.originalalex.filmdex.tmdb.movies.MoviesSearch;
import io.github.originalalex.filmdex.tmdb.movies.SearchSpecificMovie;

public class WarmUp {

    public static void warmUp() {
        MoviesSearch.getHomepageInformation();
        SearchSpecificMovie.getSpecificFilm("531");
        GeneralSearch.search("Test");
    }

}
