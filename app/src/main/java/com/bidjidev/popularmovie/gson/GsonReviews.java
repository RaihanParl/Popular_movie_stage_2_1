package com.bidjidev.popularmovie.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by You on 7/27/17.
 */

public class GsonReviews {

    @SerializedName("id")
    public Integer id;

    @SerializedName("page")
    public Integer page;

    @SerializedName("results")
    public List<Result> resultsRev = null;
    @SerializedName("total_pages")
    public Integer totalPages;

    @SerializedName("total_results")
    public Integer totalResults;

    public class Result {
        @SerializedName("id")
        public String id;

        @SerializedName("author")
        public String author;

        @SerializedName("content")
        public String content;

        @SerializedName("url")
        public String url;
    }
}
