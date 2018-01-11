package com.piotrkalitka.justMovies.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchModel {

    public static String PLOT_SHORT = "short";
    public static String PLOT_FULL = "full";

    @SerializedName("Response")
    private String response;
    @SerializedName("totalResults")
    private int totalResults;
    @SerializedName("Search")
    private List<Movie> search;

    public String getResponse() {
        return response;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Movie> getSearch() {
        return search;
    }

    public static class Movie {
        @SerializedName("Title")
        private String title;
        @SerializedName("Year")
        private String year;
        @SerializedName("imdbID")
        private String imdbID;
        @SerializedName("Type")
        private String type;
        @SerializedName("Poster")
        private String poster;

        public String getTitle() {
            return title;
        }

        public String getYear() {
            return year;
        }

        public String getImdbID() {
            return imdbID;
        }

        public String getType() {
            return type;
        }

        public String getPoster() {
            return poster;
        }
    }


}
