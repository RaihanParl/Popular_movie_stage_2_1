package com.bidjidev.popularmovie.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by You on 7/27/17.
 */

public class GsonVideo {
    @SerializedName("id")
    public Integer id;

    @SerializedName("results")
    public List<Result> results = null;

    public class Result {
        @SerializedName("id")
        public String id;

        @SerializedName("iso_639_1")
        public String iso6391;

        @SerializedName("iso_3166_1")
        public String iso31661;

        @SerializedName("key")
        public String key;

        @SerializedName("name")
        public String name;

        @SerializedName("site")
        public String site;

        @SerializedName("type")
        public String type;

        @SerializedName("size")
        public Integer size;
        @SerializedName("result")
        public Integer resul;

    }
}
